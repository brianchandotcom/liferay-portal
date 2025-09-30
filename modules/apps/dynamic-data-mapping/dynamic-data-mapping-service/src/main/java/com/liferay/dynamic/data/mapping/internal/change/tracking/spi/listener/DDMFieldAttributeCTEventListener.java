/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.change.tracking.spi.listener;

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.spi.listener.CTEventListener;
import com.liferay.dynamic.data.mapping.model.DDMFieldAttribute;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFieldAttributeUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;
import java.util.Map;

import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.OutputDocument;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = CTEventListener.class)
public class DDMFieldAttributeCTEventListener implements CTEventListener {

	@Override
	public void onBeforePublish(long ctCollectionId) {
		List<CTEntry> ctEntries = _ctEntryLocalService.getCTEntries(
			ctCollectionId,
			_portal.getClassNameId(DDMFieldAttribute.class.getName()));

		if (ctEntries.isEmpty()) {
			return;
		}

		for (CTEntry ctEntry : ctEntries) {
			DDMFieldAttribute ddmFieldAttribute =
				DDMFieldAttributeUtil.fetchByPrimaryKey(
					ctEntry.getModelClassPK());

			if (ddmFieldAttribute == null) {
				continue;
			}

			String output = ddmFieldAttribute.getAttributeValue();

			if (!output.contains("previewCTCollectionId")) {
				continue;
			}

			Source source = new Source(output);

			List<StartTag> imgTags = source.getAllStartTags("img");

			OutputDocument outputDocument = new OutputDocument(source);

			for (StartTag imgTag : imgTags) {
				Attributes attributes = imgTag.getAttributes();

				Map<String, String> map = outputDocument.replace(
					attributes, false);

				String src = attributes.getValue("src");

				int index = src.indexOf("previewCTCollectionId=");

				if (index == -1) {
					continue;
				}

				map.put("src", src.substring(0, index));
			}

			ddmFieldAttribute.setAttributeValue(outputDocument.toString());

			try {
				TransactionInvokerUtil.invoke(
					_transactionConfig,
					() -> {
						DDMFieldAttributeUtil.update(ddmFieldAttribute);

						return null;
					});
			}
			catch (Throwable throwable) {
				if (_log.isWarnEnabled()) {
					_log.warn(throwable);
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFieldAttributeCTEventListener.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private Portal _portal;

}