/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare type PageContent = {
	actions: {
		editImage?: {
			editImageURL: string;
			fileEntryId: string;
			previewURL: string;
		};
		editURL?: string;
		permissionsURL?: string;
		viewUsagesURL?: string;
	};
	className: string;
	classNameId: string;
	classPK: string;
	classTypeId: string;
	externalReferenceCode: string;
	icon: string;
	isRestricted: boolean;
	status: {
		hasApprovedVersion: boolean;
		label: string;
		style: string;
	};
	subtype: string;
	title: string;
	type: string;
	usagesCount: number;
};
export declare const pageContentsAtom: {
	readonly 'default': {
		readonly data?:
			| readonly {
					readonly actions: {
						readonly editImage?:
							| {
									readonly editImageURL: string;
									readonly fileEntryId: string;
									readonly previewURL: string;
							  }
							| undefined;
						readonly editURL?: string | undefined;
						readonly permissionsURL?: string | undefined;
						readonly viewUsagesURL?: string | undefined;
					};
					readonly className: string;
					readonly classNameId: string;
					readonly classPK: string;
					readonly classTypeId: string;
					readonly externalReferenceCode: string;
					readonly icon: string;
					readonly isRestricted: boolean;
					readonly status: {
						readonly hasApprovedVersion: boolean;
						readonly label: string;
						readonly style: string;
					};
					readonly subtype: string;
					readonly title: string;
					readonly type: string;
					readonly usagesCount: number;
			  }[]
			| undefined;
		readonly status: 'idle' | 'loading' | 'saved';
	};
	readonly 'key': string;
	readonly 'Liferay.State.ATOM': true;
};
export default function usePageContents(): readonly {
	readonly actions: {
		readonly editImage?:
			| {
					readonly editImageURL: string;
					readonly fileEntryId: string;
					readonly previewURL: string;
			  }
			| undefined;
		readonly editURL?: string | undefined;
		readonly permissionsURL?: string | undefined;
		readonly viewUsagesURL?: string | undefined;
	};
	readonly className: string;
	readonly classNameId: string;
	readonly classPK: string;
	readonly classTypeId: string;
	readonly externalReferenceCode: string;
	readonly icon: string;
	readonly isRestricted: boolean;
	readonly status: {
		readonly hasApprovedVersion: boolean;
		readonly label: string;
		readonly style: string;
	};
	readonly subtype: string;
	readonly title: string;
	readonly type: string;
	readonly usagesCount: number;
}[];
export declare function clearPageContents(): void;
