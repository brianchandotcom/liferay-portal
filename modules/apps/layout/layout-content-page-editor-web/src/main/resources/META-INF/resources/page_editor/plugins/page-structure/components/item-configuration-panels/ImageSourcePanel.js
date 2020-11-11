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

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import React, {useState} from 'react';

import {useSelector} from '../../../../app/store/index';
import isMapped from '../../../../app/utils/isMapped';
import getEditableItemPropTypes from '../../../../prop-types/getEditableItemPropTypes';
import {ImagePropertiesPanel} from './ImagePropertiesPanel';
import {MappingPanel} from './MappingPanel';

const SOURCE_OPTIONS = {
	direct: {
		label: Liferay.Language.get('direct'),
		value: 'direct',
	},
	mapping: {
		label: Liferay.Language.get('mapping'),
		value: 'mapping',
	},
};

export default function ImageSourcePanel({item}) {
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);

	const editableValue =
		fragmentEntryLinks[item.fragmentEntryLinkId].editableValues[
			item.editableValueNamespace
		][item.editableId];

	const [source, setSource] = useState(() =>
		isMapped(editableValue)
			? SOURCE_OPTIONS.mapping.value
			: SOURCE_OPTIONS.direct.value
	);

	const ConfigurationPanel =
		source === SOURCE_OPTIONS.direct.value
			? ImagePropertiesPanel
			: MappingPanel;

	return (
		<>
			<ClayForm>
				<ClaySelectWithOption
					aria-label={Liferay.Language.get('source-selection')}
					className="form-control form-control-sm mb-3"
					onChange={(event) => setSource(event.target.value)}
					options={Object.values(SOURCE_OPTIONS)}
					value={source}
				/>
			</ClayForm>

			<ConfigurationPanel item={item} />
		</>
	);
}

ImageSourcePanel.propTypes = {
	item: getEditableItemPropTypes().isRequired,
};
