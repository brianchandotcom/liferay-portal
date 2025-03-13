/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface Window {
	Liferay: {
		FeatureFlags?: {
			[key: string]: boolean;
		};
		SPA?: boolean;
		ThemeDisplay?: {
			getPathContext?: () => string;
			getUserEmailAddress?: () => string;
			getUserName?: () => string;
		};
		Util?: {
			Cookie?: {
				TYPES?: {
					[key: string]: string;
				};
				get?: (name: string) => string;
				set?: (
					key: string,
					data: string,
					type: any,
					options: {}
				) => void;
			};
			LocalStorage?: {
				TYPES?: {
					[key: string]: string;
				};
				getItem: (key: string, type: string) => void;
				removeItem: (key: string, type: string) => void;
				setItem: (key: string, value: any, type: string) => void;
			};
		};
		on?: (listener: string, callback: () => void) => void;
		once?: (listener: string, callback: () => void) => void;
	};
}
