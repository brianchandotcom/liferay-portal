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

import {
	addParams,
	navigate,
	openModal,
	openSelectionModal,
} from 'frontend-js-web';

const ACTIONS = {
	checkin({checkinURL}, portletNamespace) {
		Liferay.componentReady(
			`${portletNamespace}DocumentLibraryCheckinModal`
		).then((documentLibraryCheckinModal) => {
			documentLibraryCheckinModal.open((versionIncrease, changeLog) => {
				let portletURL = checkinURL;

				if (versionIncrease) {
					portletURL = addParams(
						`${portletNamespace}versionIncrease=${encodeURIComponent(
							versionIncrease
						)}`,
						portletURL
					);
				}

				if (changeLog) {
					portletURL = addParams(
						`${portletNamespace}changeLog=${encodeURIComponent(
							changeLog
						)}`,
						portletURL
					);
				}

				portletURL = addParams(
					`${portletNamespace}updateVersionDetails=true`,
					portletURL
				);

				navigate(portletURL);
			});
		});
	},

	compareTo({
		compareVersionURL,
		dialogTitle,
		jsNamespace,
		namespace,
		selectFileVersionURL,
	}) {
		openSelectionModal({
			id: `${jsNamespace}compareFileVersions`,
			onSelect(selectedItem) {
				let uri = compareVersionURL;

				uri = addParams(
					`${namespace}sourceFileVersionId=${selectedItem.sourceversion}`,
					uri
				);
				uri = addParams(
					`${namespace}targetFileVersionId=${selectedItem.targetversion}`,
					uri
				);

				navigate(uri);
			},
			selectEventName: `${namespace}selectFileVersionFm`,
			title: dialogTitle,
			url: selectFileVersionURL,
		});
	},

	delete({deleteURL}) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, deleteURL);
		}
	},

	deleteVersion({deleteURL}) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, deleteURL);
		}
	},

	editImage({fileEntryId, imageURL}, portletNamespace) {
		window[`${portletNamespace}editWithImageEditor`]({
			fileEntryId,
			imageURL,
		});
	},

	move({parameterName, parameterValue}, portletNamespace) {
		window[`${portletNamespace}move`](1, parameterName, parameterValue);
	},

	permissions({permissionsURL}) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: permissionsURL,
		});
	},

	publish({publishURL}) {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-publish-the-selected-document'
				)
			)
		) {
			location.href = publishURL;
		}
	},
};

export default function propsTransformer({items, portletNamespace, ...props}) {
	return {
		...props,
		items: items.map((item) => {
			return {
				...item,
				items: item.items?.map((child) => ({
					...child,
					onClick(event) {
						const action = child.data?.action;

						if (action) {
							event.preventDefault();

							ACTIONS[action](child.data, portletNamespace);
						}
					},
				})),
			};
		}),
	};
}
