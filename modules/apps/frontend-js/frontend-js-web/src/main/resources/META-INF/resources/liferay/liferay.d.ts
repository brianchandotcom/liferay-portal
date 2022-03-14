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

declare module Liferay {
	namespace Address {

		/* Returns a list of countries */
		export function getCountries(callback?: () => void): Promise<any>;

		/* Returns a list of regions by country */
		export function getRegions(
			callback?: () => void,
			selectKey?: string
		): Promise<any>;
	}

	namespace Util {
		namespace PortletURL {

			/* Returns an action portlet URL in form of a URL object by setting the lifecycle parameter */
			export function createActionURL(
				basePortletURL: string,
				parameters?: Object
			): URL;

			/* Returns a portlet URL in form of a URL Object */
			export function createPortletURL(
				basePortletURL: string,
				parameters?: Object
			): URL;

			/* Returns a render portlet URL in form of a URL object by setting the lifecycle parameter */
			export function createRenderURL(
				basePortletURL: string,
				parameters?: Object
			): URL;

			/* Returns a resource portlet URL in form of a URL object by setting the lifecycle parameter */
			export function createResourceURL(
				basePortletURL: string,
				parameters?: Object
			): URL;
		}

		namespace Session {

			/**
			 * Gets the Store utility fetch value for given key
			 */
			export function get(
				key: string,
				options?: {useHttpSession: boolean; [key: string]: any}
			): Promise<any>;

			/**
			 * Sets the Store utility fetch value
			 */
			export function set(
				key: string,
				value: Object | string,
				options?: {useHttpSession: boolean; [key: string]: any}
			): Promise<any>;
		}

		/* Escapes HTML from the given string */
		export function escapeHTML(string: string): string;

		/**
		 * Fetches a resource. A thin wrapper around ES6 Fetch API, with standardized
		 * default configuration.
		 */
		export function fetch(
			resource: string | Request,
			init?: Object
		): Promise<any>;

		/* Returns storage number formatted as a String */
		export function formatStorage(size: number, options?: Object): string;

		/* Returns a formatted XML */
		export function formatXML(content: string, options?: Object): string;

		/**
		 * Returns dimensions and coordinates representing a cropped region
		 */
		export function getCropRegion(
			imagePreview: HTMLImageElement,
			region: {
				height: number;
				width: number;
				x: number;
				y: number;
			}
		): {
			height: number;
			width: number;
			x: number;
			y: number;
		};

		/**
		 * Returns a DOM element or elements in a form.
		 */
		export function getFormElement(
			form: HTMLFormElement,
			elementName: string
		): Element | NodeList | null;

		export function getLexiconIcon(
			icon: string,
			cssClass?: string
		): HTMLElement;

		export function getOpener(): any;

		/**
		 * Returns the portlet namespace with underscores prepended and appended to it
		 */
		export function getPortletNamespace(portletId: string): string;

		/**
		 * Performs navigation to the given url. If SPA is enabled, it will route the
		 * request through the SPA engine. If not, it will simple change the document
		 * location.
		 */
		export function navigate(url: string | URL, listeners?: Object): void;

		/* Returns a namespaced string taking into account the optional parameters inside the provided object */
		export function ns(namespace: string, object?: Object): string | Object;

		/* Returns a FormData containing serialized object. */
		export function objectToFormData(
			object: Object,
			formData?: FormData,
			namespace?: string
		): FormData;

		/**
		 * Returns a FormData containing serialized object.
		 */

		export function objectToURLSearchParams(
			object: Object
		): URLSearchParams;

		/**
		 * Submits the form, with optional setting of form elements.
		 */
		export function postForm(
			form: HTMLFormElement | string,
			options?: {data: Object; url: string}
		): void;

		/**
		 * Sets the form elements to given values.
		 */
		export function setFormValues(
			form: HTMLFormElement,
			data: Object
		): void;

		export function sub(langKey: string, ...args: any[]): string;

		/**
		 * Get character code at the start of the given string.
		 */
		export function toCharCode(name: string): string;

		/**
		 * Unescapes HTML from the given string.
		 */
		export function unescapeHTML(string: string): string;

		export function openModal(props: Object): void;

		export function openPortletModal(
			containerProps: Object,
			footerCssClass: string,
			headerCssClass: string,
			iframeBodyCssClass: string,
			onClose: () => void,
			portletSelector: string,
			subTitle: string,
			title: string,
			url: string
		): void;

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

		/**
		 * Function that implements the Toast pattern, which allows to present feedback
		 * to user actions as a toast message in the lower left corner of the page
		 */
		export function openToast({
			autoClose,
			container,
			containerId,
			message,
			onClick,
			onClose,
			renderData,
			title,
			toastProps,
			type,
			variant,
		}: {
			autoClose?: number | boolean;
			container?: HTMLElement;
			containerId?: string;
			message?: string;
			onClick?: () => void;
			onClose?: () => void;
			renderData?: {portletId: string};
			title?: string;
			toastProps?: Object;
			type?: string;
			variant?: string;
		}): void;
	}

	namespace Portal {
		namespace Tabs {

			/**
			 * Prepares and fires the an event that will show a tab
			 */
			export function show(
				namespace: string,
				names: string[],
				id: string,
				callback?: () => void
			): void;
		}
	}

	namespace Portlet {
		export function add(options: object): void;

		export function addHTML(options: object): void;

		export function close(
			portlet: any,
			skipConfirm: boolean,
			options?: object
		): void;

		export function destroy(portlet: any, options?: object): void;

		export function onLoad(options: object): void;

		export function refresh(
			portlet: any,
			data?: object,
			mergeWithRefreshURLData?: boolean
		): void;

		export function registerStatic(portletId: any): void;

		/**
		 * Minimizes portlet
		 */
		export function minimize(
			portletSelector: string,
			trigger: HTMLElement,
			options?: Object
		): void;
	}

	namespace Service {
		export function get(httpMethodName: string): void;

		export function del(httpMethodName: string): void;

		export function post(httpMethodName: string): void;

		export function put(httpMethodName: string): void;

		export function update(httpMethodName: string): void;

		export function bind(...args: any[]): void;

		export function parseInvokeArgs(payload: Object, ...args: any[]): void;

		export function parseIOConfig(payload: Object, ...args: any[]): void;

		export function parseIOFormConfig(
			ioConfig: Object,
			form: HTMLFormElement,
			...args: any[]
		): void;

		export function parseStringPayload(...args: any[]): Object;

		export function invoke(
			payload: Object,
			ioConfig: Object
		): Promise<void>;
	}

	namespace State {
		type Primitive =
			| bigint
			| boolean
			| null
			| number
			| string
			| symbol
			| undefined;

		type Builtin = Date | Error | Function | Primitive | RegExp;

		/**
		 * A local "DeepReadonly" until TypeScript bundles one out of the box.
		 *
		 * See: https://github.com/microsoft/TypeScript/issues/13923
		 */
		export type Immutable<T> = T extends Builtin
			? T
			: T extends Map<infer K, infer V>
			? ReadonlyMap<Immutable<K>, Immutable<V>>
			: T extends ReadonlyMap<infer K, infer V>
			? ReadonlyMap<Immutable<K>, Immutable<V>>
			: T extends WeakMap<infer K, infer V>
			? WeakMap<Immutable<K>, Immutable<V>>
			: T extends Set<infer U>
			? ReadonlySet<Immutable<U>>
			: T extends ReadonlySet<infer U>
			? ReadonlySet<Immutable<U>>
			: T extends WeakSet<infer U>
			? WeakSet<Immutable<U>>
			: T extends Promise<infer U>
			? Promise<Immutable<U>>
			: T extends {}
			? {readonly [K in keyof T]: Immutable<T[K]>}
			: Readonly<T>;

		const ATOM = 'Liferay.State.ATOM';
		const SELECTOR = 'Liferay.State.SELECTOR';

		type Atom<T> = Immutable<{
			[ATOM]: true;
			default: T;
			key: string;
		}>;

		interface Getter {
			<T>(atomOrSelector: Atom<T> | Selector<T>): Immutable<T>;
		}

		type Selector<T> = Immutable<{
			[SELECTOR]: true;
			deriveValue: (get: Getter) => T;
			key: string;
		}>;

		export function atom<T>(key: string, value: T): Atom<T>;

		export function read<T>(
			atomOrSelector: Atom<T> | Selector<T>
		): Immutable<T>;

		export function readAtom<T>(atom: Atom<T>): Immutable<T>;

		export function readSelector<T>(selector: Selector<T>): Immutable<T>;

		export function selector<T>(
			key: string,
			deriveValue: (get: Getter) => T
		): Selector<T>;

		export function subscribe<T extends any>(
			atomOrSelector: Atom<T> | Selector<T>,
			callback: (value: Immutable<T>) => void
		): {dispose: () => void};

		export function write<T>(
			atomOrSelector: Atom<T> | Selector<T>,
			value: T
		): void;

		export function writeAtom<T>(atom: Atom<T>, value: T): void;
	}

	namespace DOMTaskRunner {
		export function addTask(task: object): void;

		export function addTaskState(state: object): void;

		export function reset(): void;

		export function runTasks(node: any): void;
	}

	namespace Language {
		type Locale =
			| 'ar_SA'
			| 'ca_ES'
			| 'de_DE'
			| 'en_US'
			| 'es_ES'
			| 'fi_FI'
			| 'fr_FR'
			| 'hu_HU'
			| 'nl_NL'
			| 'ja_JP'
			| 'pt_BR'
			| 'sv_SE'
			| 'zh_CN';

		type LocalizedValue<T> = {[key in Locale]?: T};

		type Direction = 'ltr' | 'rtl';

		export function get(key: string): string;

		export const direction: LocalizedValue<Direction>;

		export const available: Object;
	}

	namespace ThemeDisplay {
		export function getDefaultLanguageId(): string;
		export function getLanguageId(): Language.Locale;
	}

	/**
	 * Registers a component and retrieves its instance from the global registry.
	 */
	export function component(
		id: string,
		value?: Object,
		componentConfig?: Object
	): Object;

	/**
	 * Retrieves a list of component instances after they've been registered.
	 */
	export function componentReady(...componentIds: string[]): Promise<any>;

	/**
	 * Destroys the component registered by the provided component ID. This invokes
	 * the component's own destroy lifecycle methods (destroy or dispose) and
	 * deletes the internal references to the component in the component registry.
	 */
	export function destroyComponent(componentId: string): void;

	/**
	 * Destroys registered components matching the provided filter function. If no
	 * filter function is provided, it destroys all registered components.
	 */
	export function destroyComponents(
		filterFn?: (component: any, componentConfigs: any) => boolean
	): void;

	/**
	 * Clears the component promises map to make sure pending promises don't get
	 * accidentally resolved at a later stage if a component with the same ID
	 * appears, causing stale code to run.
	 */
	export function destroyUnfulfilledPromises(): void;

	/**
	 * Retrieves a registered component's cached state.
	 */
	export function getComponentCache(componentId: string): void;

	/**
	 * Initializes the component cache mechanism.
	 */
	export function initComponentCache(): void;

	export function SideNavigation(
		toggler: HTMLElement,
		options?: Object
	): void;

	export function namespace(object: Object, path: string): Object;

	export function lazyLoad(): void;

	export function on(events: string | string[], callback?: () => void): void;

	export function detach(event: string, callback?: () => void): void;
}
interface ThemeDisplay {
	isStatePopUp(): boolean;
}

interface Window {
	cancelIdleCallback(handle: number): void;

	requestIdleCallback(callback: Function): any;

	themeDisplay: ThemeDisplay;
}
