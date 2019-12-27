/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.internal.template.util;

import com.liferay.layout.util.template.LayoutColumn;
import com.liferay.layout.util.template.LayoutConverter;
import com.liferay.layout.util.template.LayoutData;
import com.liferay.layout.util.template.LayoutRow;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true, property = "layout.template.id=default",
	service = LayoutConverter.class
)
public class DefaultLayoutConverter implements LayoutConverter {

	@Override
	public LayoutData convert(Layout layout) {
		try {
			return _convert(layout);
		}
		catch (DocumentException de) {
			throw new RuntimeException(de);
		}
	}

	private LayoutData _convert(Layout layout) throws DocumentException {
		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		LayoutTemplate layoutTemplate = layoutTypePortlet.getLayoutTemplate();

		Document document = SAXReaderUtil.read(layoutTemplate.getContent());

		XPath rowsXPath = SAXReaderUtil.createXPath(
			"//div[contains(@class, 'portlet-layout') and contains(@class, " +
				"'row')]");

		List<Node> rowNodes = rowsXPath.selectNodes(document);

		List<UnsafeConsumer<LayoutRow, Exception>> rowUnsafeConsumers =
			new ArrayList<>();

		int columnIndex = 1;

		XPath columnsXPath = SAXReaderUtil.createXPath(
			"child::div[contains(@class, 'portlet-column')]");

		for (Node rowNode : rowNodes) {
			List<Node> columnNodes = columnsXPath.selectNodes(rowNode);

			List<UnsafeConsumer<LayoutColumn, Exception>>
				columnUnsafeConsumers = new ArrayList<>();

			for (Node columnNode : columnNodes) {
				String columnXML = columnNode.asXML();

				Document columnDocument = SAXReaderUtil.read(columnXML);

				Element element = columnDocument.getRootElement();

				Attribute attribute = element.attribute("class");

				String attributeData = (String)attribute.getData();

				String[] attributeParts = attributeData.split(StringPool.SPACE);

				String[] filteredAttributeParts = ArrayUtil.filter(
					attributeParts, s -> s.startsWith("col-md-"));

				if (filteredAttributeParts.length > 0) {
					String columnId =
						LayoutTypePortletConstants.COLUMN_PREFIX + columnIndex;

					int columnSize = GetterUtil.getInteger(
						filteredAttributeParts[0].substring(
							"col-md-".length()));

					UnsafeConsumer<LayoutColumn, Exception>
						columnUnsafeConsumer = layoutColumn -> {
							layoutColumn.addPortlets(columnId);
							layoutColumn.setSize(columnSize);
						};

					columnUnsafeConsumers.add(columnUnsafeConsumer);
				}

				columnIndex++;
			}

			UnsafeConsumer<LayoutRow, Exception> rowUnsafeConsumer =
				layoutRow -> layoutRow.addLayoutColumns(
					columnUnsafeConsumers.toArray(new UnsafeConsumer[0]));

			rowUnsafeConsumers.add(rowUnsafeConsumer);
		}

		return LayoutData.of(
			layout, rowUnsafeConsumers.toArray(new UnsafeConsumer[0]));
	}

}