declare module 'redux-toolbox';

declare module 'clipboard' {
	interface ClipboardEvent {
		action: string;
		clearSelection: () => void;
		text: string;
		trigger: Element;
	}

	type ClipboardEventName = 'success' | 'error';

	class Clipboard {
		constructor(
			// eslint-disable-next-line no-undef
			selector: string | Element | NodeListOf<Element>,
			options?: {[key: string]: any}
		);
		on(
			event: ClipboardEventName,
			callback: (e: ClipboardEvent) => void
		): this;
		destroy(): void;
	}

	export default Clipboard;
}
