/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal;

import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManager;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(service = DDMStructureManager.class)
public class DDMStructureManagerImpl implements DDMStructureManager {

	@Override
	public DDMStructure addStructure(
			long userId, long groupId, String parentStructureKey,
			long classNameId, String structureKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, DDMForm ddmForm,
			String storageType, int type, ServiceContext serviceContext)
		throws PortalException {

		com.liferay.dynamic.data.mapping.model.DDMForm translatedDDMForm =
			_ddmBeanTranslator.translate(ddmForm);

		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.addStructure(
				userId, groupId, parentStructureKey, classNameId, structureKey,
				nameMap, descriptionMap, translatedDDMForm,
				_ddm.getDefaultDDMFormLayout(translatedDDMForm), storageType,
				type, serviceContext);

		return new DDMStructureImpl(ddmStructure);
	}

	@Override
	public void deleteStructure(long structureId) throws PortalException {
		_ddmStructureLocalService.deleteStructure(structureId);
	}

	@Override
	public DDMStructure fetchStructure(long structureId) {
		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.fetchDDMStructure(structureId);

		if (ddmStructure == null) {
			return null;
		}

		return new DDMStructureImpl(ddmStructure);
	}

	@Override
	public DDMStructure fetchStructure(
		long groupId, long classNameId, String structureKey) {

		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.fetchStructure(
				groupId, classNameId, structureKey);

		if (ddmStructure == null) {
			return null;
		}

		return new DDMStructureImpl(ddmStructure);
	}

	@Override
	public List<DDMStructure> getClassStructures(
		long companyId, long classNameId) {

		List<DDMStructure> ddmStructures = new ArrayList<>();

		List<com.liferay.dynamic.data.mapping.model.DDMStructure> structures =
			_ddmStructureLocalService.getClassStructures(
				companyId, classNameId);

		for (com.liferay.dynamic.data.mapping.model.DDMStructure structure :
				structures) {

			ddmStructures.add(new DDMStructureImpl(structure));
		}

		return ddmStructures;
	}

	@Override
	public DDMStructure updateStructure(
			long userId, long structureId, long parentStructureId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			DDMForm ddmForm, ServiceContext serviceContext)
		throws PortalException {

		com.liferay.dynamic.data.mapping.model.DDMForm copyDDMForm =
			_ddmBeanTranslator.translate(ddmForm);

		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.updateStructure(
				userId, structureId, parentStructureId, nameMap, descriptionMap,
				copyDDMForm, _ddm.getDefaultDDMFormLayout(copyDDMForm),
				serviceContext);

		return new DDMStructureImpl(ddmStructure);
	}

	@Override
	public void updateStructureKey(long structureId, String structureKey)
		throws PortalException {

		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.getDDMStructure(structureId);

		ddmStructure.setStructureKey(structureKey);

		_ddmStructureLocalService.updateDDMStructure(ddmStructure);
	}

	@Reference
	private DDM _ddm;

	@Reference
	private DDMBeanTranslator _ddmBeanTranslator;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

}