/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import useSetRef from '../../../common/hooks/useSetRef';
import {getLayoutDataItemPropTypes} from '../../../prop_types/index';
import ManageAllowedFragmentButton from '../ManageAllowedFragmentButton';
import Topper from '../topper/Topper';

const DropZone = React.forwardRef(({item}, ref) => {
	return (
		<div className="cadmin">
			<div
				className="align-items-center bg-lighter d-flex flex-column justify-content-center page-editor__drop-zone text-3 text-center text-secondary"
				ref={ref}
			>
				<p>
					{Liferay.Language.get(
						'fragments-and-widgets-for-pages-based-on-this-master-will-be-placed-here'
					)}
				</p>

				<ManageAllowedFragmentButton item={item} />
			</div>
		</div>
	);
});

DropZone.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

const DropZoneWithControls = React.forwardRef(({item}, ref) => {
	const [setRef, itemElement] = useSetRef(ref);

	return (
		<Topper active item={item} itemElement={itemElement}>
			<DropZone item={item} ref={setRef} />
		</Topper>
	);
});

DropZoneWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export default DropZoneWithControls;
