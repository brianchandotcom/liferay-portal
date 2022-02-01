/* eslint-disable */
/// <reference path="apps/frontend-js/frontend-js-collapse-support-web/src/main/resources/META-INF/resources/liferay.d.ts" />
/// <reference path="apps/frontend-js/frontend-js-dropdown-support-web/src/main/resources/META-INF/resources/liferay.d.ts" />
/// <reference path="apps/frontend-js/frontend-js-tabs-support-web/src/main/resources/META-INF/resources/liferay.d.ts" />
/// <reference path="apps/frontend-js/frontend-js-web/src/main/resources/META-INF/resources/liferay/liferay.d.ts" />

declare module Liferay {
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
