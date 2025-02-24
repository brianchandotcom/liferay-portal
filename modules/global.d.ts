/* eslint-disable */
/// <reference path="apps/commerce/commerce-frontend-js/src/main/resources/META-INF/resources/liferay.d.ts" />
/// <reference path="apps/frontend-js/frontend-js-bootstrap-support-web/src/main/resources/META-INF/resources/liferay.d.ts" />
/// <reference path="apps/frontend-js/frontend-js-web/src/main/resources/META-INF/resources/liferay/liferay.d.ts" />
/// <reference path="apps/frontend-icons/frontend-icons-web/src/main/resources/META-INF/resources/js/liferay.d.ts" />
/// <reference path="apps/oauth2-provider/oauth2-provider-web/src/main/resources/META-INF/resources/js/liferay.d.ts" />

declare module Liferay {
	interface Event {
		id: string;
	}

	interface EventHandler {
		detach: () => number;
	}

	export const FeatureFlags: {
		[key: string]: boolean;
	};

	namespace Portlet {
		export function openModal(...args: any[]): void;

		export function openWindow(...args: any[]): void;
	}

	export const SPA: any;

	namespace Util {
		export function openAlertModal(...args: any[]): void;

		export function openConfirmModal(...args: any[]): void;

		export function openModal(props: Object): void;

		export function openSelectionModal(
			buttonAddLabel: string,
			buttonCancelLabel: string,
			containerProps: Object,
			customSelectEvent: boolean,
			height: string,
			id: string,
			iframeBodyCssClass: string,
			multiple: boolean,
			onClose: () => void,
			onSelect: () => void,
			selectEventName: string,
			selectedData: any,
			size: 'full-screen' | 'lg' | 'md' | 'sm',
			title: string,
			url: string,
			zIndex: number
		): void;

		export function openSimpleInputModal(...args: any[]): void;

		export function openToast(...args: any[]): void;
	}

	export function detach(
		event: string | EventHandler,
		callback?: () => void
	): void;

	export function fire(type: string, context?: any): void;

	export function on(
		events: string | string[],
		callback?: (event?: LiferayEvent) => void
	): EventHandler;

	export function once(
		events: string | string[],
		callback?: (event?: LiferayEvent) => void
	): EventHandler;
}
