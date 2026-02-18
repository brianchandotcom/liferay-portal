/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ResourcedModel;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.search.indexer.BaseModelDocumentFactory;
import com.liferay.portal.search.model.uid.UIDFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = BaseModelDocumentFactory.class)
public class BaseModelDocumentFactoryImpl implements BaseModelDocumentFactory {

	@Override
	public Document createDocument(BaseModel<?> baseModel) {
		Document document = new DocumentImpl();

		Tuple classPKResourcePrimKeyTuple = _getClassPKResourcePrimKey(
			baseModel);

		document.add(
			new Field(Field.ENTRY_CLASS_NAME, baseModel.getModelClassName()));

		Long entryClassPK = (Long)classPKResourcePrimKeyTuple.getObject(0);

		if (entryClassPK != null) {
			document.add(
				new Field(Field.ENTRY_CLASS_PK, String.valueOf(entryClassPK)));
		}

		Long rootEntryClassPK = _getRootEntryClassPK(
			classPKResourcePrimKeyTuple);

		if (rootEntryClassPK != null) {
			document.add(
				new Field(
					Field.ROOT_ENTRY_CLASS_PK,
					String.valueOf(rootEntryClassPK)));
		}

		document.add(new Field(Field.UID, uidFactory.getUID(baseModel)));

		return document;
	}

	@Reference
	protected UIDFactory uidFactory;

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

		return new Tuple(
			_getEntryClassPK(baseModel, baseModel.getModelClassName(), classPK),
			resourcePrimKey);
	}

	private <T> long _getEntryClassPK(T entry, String className, long classPK) {
		AssetRendererFactory<T> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if (assetRendererFactory == null) {
			return classPK;
		}

		return assetRendererFactory.getAssetEntryClassPK(entry);
	}

	private Long _getRootEntryClassPK(Tuple classPKResourcePrimKeyTuple) {
		long resourcePrimKey = (Long)classPKResourcePrimKeyTuple.getObject(1);

		if (resourcePrimKey > 0) {
			return resourcePrimKey;
		}

		return null;
	}

}