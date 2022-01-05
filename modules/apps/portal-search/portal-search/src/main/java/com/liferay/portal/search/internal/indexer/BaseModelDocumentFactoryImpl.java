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

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ResourcedModel;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.indexer.BaseModelDocumentFactory;
import com.liferay.portal.search.model.uid.UIDFactory;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = BaseModelDocumentFactory.class)
public class BaseModelDocumentFactoryImpl implements BaseModelDocumentFactory {

	@Override
	public com.liferay.portal.kernel.search.Document createDocument(
		BaseModel<?> baseModel) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		Tuple classPKResourcePrimKeyTuple = _getClassPKResourcePrimKey(
			baseModel);

		documentBuilder.setString(
			Field.ENTRY_CLASS_NAME, baseModel.getModelClassName()
		).setLong(
			Field.ENTRY_CLASS_PK, (Long)classPKResourcePrimKeyTuple.getObject(0)
		).setLong(
			Field.ROOT_ENTRY_CLASS_PK,
			_getRootEntryClassPK(classPKResourcePrimKeyTuple)
		);

		uidFactory.setUID(baseModel, documentBuilder);

		Document document = documentBuilder.build();

		_enforceStandardUID(document);

		return _toLegacyDocument(document);
	}

	@Reference
	protected DocumentBuilderFactory documentBuilderFactory;

	@Reference
	protected UIDFactory uidFactory;

	private void _enforceStandardUID(Document document) {
		uidFactory.getUID(document);
	}

	private Tuple _getClassPKResourcePrimKey(BaseModel<?> baseModel) {
		long classPK = 0;
		long resourcePrimKey = 0;

		if (baseModel instanceof ResourcedModel) {
			ResourcedModel resourcedModel = (ResourcedModel)baseModel;

			classPK = resourcedModel.getResourcePrimKey();
			resourcePrimKey = resourcedModel.getResourcePrimKey();
		}
		else {
			classPK = (Long)baseModel.getPrimaryKeyObj();
		}

		return new Tuple(classPK, resourcePrimKey);
	}

	private Long _getRootEntryClassPK(Tuple classPKResourcePrimKeyTuple) {
		long resourcePrimKey = (Long)classPKResourcePrimKeyTuple.getObject(1);

		if (resourcePrimKey > 0) {
			return resourcePrimKey;
		}

		return null;
	}

	private com.liferay.portal.kernel.search.Document _toLegacyDocument(
		Document document) {

		DocumentImpl documentImpl = new DocumentImpl();

		Map<String, com.liferay.portal.search.document.Field> fields =
			document.getFields();

		fields.forEach(
			(key, field) -> documentImpl.add(
				new Field(key, String.valueOf(field.getValue()))));

		return documentImpl;
	}

}