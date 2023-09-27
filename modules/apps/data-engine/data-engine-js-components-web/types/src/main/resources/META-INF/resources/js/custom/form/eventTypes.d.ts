/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare const EVENT_TYPES: {
	FORM_BUILDER: {
		FOCUSED_FIELD: {
			CHANGE: string;
		};
		PAGES: {
			UPDATE: string;
		};
	};
	FORM_VIEW: {
		REPEATABLE_FIELD: {
			CHANGE_ORDER: string;
		};
	};
	OBJECT: {
		FIELDS_CHANGE: string;
		RELATIONSHIPS_CHANGE: string;
	};
	PAGE: {
		ADD: string;
		DELETE: string;
		DESCRIPTION_CHANGE: string;
		RESET: string;
		SWAP: string;
		TITLE_CHANGE: string;
	};
	PAGINATION: {
		CHANGE: string;
		NEXT: string;
		PREVIOUS: string;
	};
	RULES: {
		UPDATE: string;
	};
	SUCCESS_PAGE: string;
};
