/* eslint-disable */
/// <reference path="apps/commerce/commerce-frontend-js/src/main/resources/META-INF/resources/liferay.d.ts" />
/// <reference path="apps/frontend-js/frontend-js-collapse-support-web/src/main/resources/META-INF/resources/liferay.d.ts" />
/// <reference path="apps/frontend-js/frontend-js-dropdown-support-web/src/main/resources/META-INF/resources/liferay.d.ts" />
/// <reference path="apps/frontend-js/frontend-js-tabs-support-web/src/main/resources/META-INF/resources/liferay.d.ts" />
/// <reference path="apps/frontend-js/frontend-js-web/src/main/resources/META-INF/resources/liferay/liferay.d.ts" />
/// <reference path="apps/frontend-icons/frontend-icons-web/src/main/resources/META-INF/resources/js/liferay.d.ts" />
/// <reference path="apps/oauth2-provider/oauth2-provider-web/src/main/resources/META-INF/resources/js/liferay.d.ts" />

declare module Liferay {
	export const FeatureFlags: {[key: string]: boolean};
	export function fire(type: string, context?: any): void;
	export function on(
		type: string,
		fn: (event: any) => void,
		context?: any
	): void;
	export function once(
		type: string,
		fn: (event: any) => void,
		context?: any
	): void;
}
