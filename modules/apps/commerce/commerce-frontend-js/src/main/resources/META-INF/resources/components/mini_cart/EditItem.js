/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {sub} from 'frontend-js-web';
import React, {useContext} from 'react';

import MiniCartContext from './MiniCartContext';

function EditItem() {
	const {editedItem, setEditedItem} = useContext(MiniCartContext);
	const backLabel = sub(
		Liferay.Language.get('go-to-x'),
		Liferay.Language.get('products')
	);

	return (
		<>
			<div className="d-flex flex-column h-100 mini-cart-edit-item">
				<div className="align-items-center d-flex mini-cart-header px-4 py-1">
					<ClayButtonWithIcon
						aria-label={backLabel}
						displayType="unstyled"
						onClick={() => setEditedItem(null)}
						symbol="angle-left"
						title={backLabel}
					/>

					<span className="font-weight-bold ml-2 text-5">
						{editedItem.name}
					</span>
				</div>

				<div className="flex-grow-1 p-4"></div>

				<div className="mini-cart-footer p-4 text-right">
					<ClayButton
						className="mr-3"
						displayType="secondary"
						onClick={() => setEditedItem(null)}
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>

					<ClayButton>{Liferay.Language.get('save')}</ClayButton>
				</div>
			</div>
		</>
	);
}

export default EditItem;
