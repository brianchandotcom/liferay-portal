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

import ClayForm from '@clayui/form';
import classNames from 'classnames';
import {ConfigProvider} from 'data-engine-js-components-web';
import DocumentLibrary from 'dynamic-data-mapping-form-field-type/DocumentLibrary/DocumentLibrary.es';
import React, {useContext} from 'react';

import {AppContext} from '../../AppContext';
import {ErrorFeedback} from '../form/FormBase';

const FormDocumentLibraryInput = ({error, onChange}) => {
	const {portletNamespace} = useContext(AppContext);

	const getDocumentLibrarySelectorURL = () => {
		const criterionJSON = {
			desiredItemSelectorReturnTypes:
				'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType,com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType',
		};

		const documentLibrarySelectorParameters = {
			'0_json': JSON.stringify(criterionJSON),
			criteria:
				'com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion',
			itemSelectedEventName: `${portletNamespace}selectDocumentLibrary`,
			p_p_id: Liferay.PortletKeys.ITEM_SELECTOR,
			p_p_state: 'pop_up',
			refererGroupId: Liferay.ThemeDisplay.getSiteGroupId(),
		};

		const documentLibrarySelectorURL = Liferay.Util.PortletURL.createPortletURL(
			themeDisplay.getLayoutRelativeControlPanelURL(),
			documentLibrarySelectorParameters
		);

		return documentLibrarySelectorURL.toString();
	};

	return (
		<div className="document-library-form">
			<ClayForm.Group
				className={classNames({
					'has-error': error,
				})}
			>
				<label>{Liferay.Language.get('document')}</label>
				<ConfigProvider config={{portletNamespace}}>
					<DocumentLibrary
						itemSelectorURL={getDocumentLibrarySelectorURL()}
						modalTitle={Liferay.Language.get('select-document')}
						onChange={onChange}
						visible
					/>
				</ConfigProvider>

				{error && <ErrorFeedback className="mt-n3" error={error} />}
			</ClayForm.Group>
		</div>
	);
};

export default FormDocumentLibraryInput;
