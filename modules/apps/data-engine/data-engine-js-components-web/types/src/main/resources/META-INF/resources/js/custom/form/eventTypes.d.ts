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
