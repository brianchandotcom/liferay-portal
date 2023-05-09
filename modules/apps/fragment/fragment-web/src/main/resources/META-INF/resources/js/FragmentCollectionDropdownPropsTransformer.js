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

import {render} from '@liferay/frontend-js-react-web';
import {openModal} from 'frontend-js-web';
import {unmountComponentAtNode} from 'react-dom';

import ImportModal from './ImportModal';
import openDeleteFragmentCollectionModal from './openDeleteFragmentCollectionModal';

const ACTIONS = {
	deleteFragmentCollection({deleteFragmentCollectionURL}) {
		openDeleteFragmentCollectionModal({
			onDelete: () => {
				submitForm(document.hrefFm, deleteFragmentCollectionURL);
			},
		});
	},
	openImportCollectionView({portletNamespace, viewImportURL}) {
		if (Liferay.FeatureFlags['LPS-174939']) {
			const modalContainer = document.createElement('div');
			modalContainer.classList.add('cadmin');
			document.body.appendChild(modalContainer);

			const disposeModal = () => {
				if (modalContainer) {
					unmountComponentAtNode(modalContainer);
					document.body.removeChild(modalContainer);
				}
			};

			render(
				ImportModal,
				{
					disposeModal,
					portletNamespace,
				},
				modalContainer
			);
		}
		else {
			openModal({
				buttons: [
					{
						displayType: 'secondary',
						label: Liferay.Language.get('cancel'),
						type: 'cancel',
					},
					{
						label: Liferay.Language.get('import'),
						type: 'submit',
					},
				],
				onClose: () => {
					window.location.reload();
				},
				title: Liferay.Language.get('import'),
				url: viewImportURL,
			});
		}
	},
};

export default function propsTransformer({items, ...props}) {
	return {
		...props,
		items: items.map((item) => {
			return {
				...item,
				items: item.items?.map((child) => {
					return {
						...child,
						onClick(event) {
							const action = child.data?.action;

							if (action) {
								event.preventDefault();

								ACTIONS[action](child.data);
							}
						},
					};
				}),
			};
		}),
	};
}
