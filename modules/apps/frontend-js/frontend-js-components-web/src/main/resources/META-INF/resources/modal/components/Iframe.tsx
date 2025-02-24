/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {delegate} from 'frontend-js-web';
import React, {RefObject} from 'react';

import type {Disposable, IframeWindow} from '../types';

const CSS_CLASS_IFRAME_BODY = 'dialog-iframe-popup';

export interface IframeOnOpen {
	(args: {
		processClose(): void;
		container?: HTMLDivElement;
		iframeWindow?: IframeWindow;
	}): void;
}

export interface IframeIframeProps {
	[propName: string]: string;
}

export interface IframeProps {
	iframeBodyCssClass?: string;
	iframeProps?: IframeIframeProps;
	onOpen?: IframeOnOpen;
	processClose: () => void;
	src?: string;
	title?: string;
	updateLoading: (loading: boolean) => void;
	url: URL | string;
}

export interface IframeState {
	src: string;
}

export default class Iframe extends React.Component<IframeProps, IframeState> {
	delegateHandlers: Disposable[] | null;
	iframeRef: RefObject<HTMLIFrameElement>;
	spaNavigationHandlers?: Liferay.EventHandler[];

	constructor(props: IframeProps) {
		super(props);

		this.delegateHandlers = [];

		this.iframeRef = React.createRef();

		const iframeURL = new URL(props.url);

		const namespace = iframeURL.searchParams.get('p_p_id');

		const bodyCssClass =
			props.iframeBodyCssClass || props.iframeBodyCssClass === ''
				? `${CSS_CLASS_IFRAME_BODY} ${props.iframeBodyCssClass}`
				: `cadmin ${CSS_CLASS_IFRAME_BODY}`;

		iframeURL.searchParams.set(`_${namespace}_bodyCssClass`, bodyCssClass);

		this.state = {src: iframeURL.toString()};
	}

	componentWillUnmount() {
		if (this.spaNavigationHandlers) {
			this.spaNavigationHandlers.forEach((handler) => {
				Liferay.detach(handler);
			});
		}

		if (this.delegateHandlers?.length) {
			this.delegateHandlers.forEach(({dispose}) => dispose());
			this.delegateHandlers = null;
		}
	}

	onLoadHandler = () => {
		const iframeWindow = this.iframeRef.current?.contentWindow as
			| IframeWindow
			| undefined;

		if (!iframeWindow) {
			console.error('IframeWindow is null');

			return;
		}

		const element = iframeWindow?.document;

		if (!element) {
			console.error(`IframeWindow's document is null`);

			return;
		}

		this.delegateHandlers?.push(
			delegate(element, 'click', '.btn-cancel,.lfr-hide-dialog', () =>
				this.props.processClose()
			),
			delegate(element, 'keydown', 'body', (event) => {
				if (event.key === 'Escape') {
					this.props.processClose();
				}
			})
		);

		iframeWindow.document.body.classList.add(CSS_CLASS_IFRAME_BODY);

		if (iframeWindow.Liferay.SPA) {
			this.spaNavigationHandlers = [
				iframeWindow.Liferay.on('beforeScreenFlip', () => {
					iframeWindow.document.body.classList.add(
						CSS_CLASS_IFRAME_BODY
					);
				}),
			];

			this.spaNavigationHandlers.push(
				iframeWindow.Liferay.on('screenFlip', () => {
					if (this.props.onOpen) {
						this.props.onOpen({
							iframeWindow,
							processClose: this.props.processClose,
						});
					}
				})
			);
		}

		this.props.updateLoading(false);

		iframeWindow.onunload = () => {
			this.props.updateLoading(true);
		};

		Liferay.fire('modalIframeLoaded', {src: this.state.src});

		if (this.props.onOpen) {
			this.props.onOpen({
				iframeWindow,
				processClose: this.props.processClose,
			});
		}
	};

	render() {
		return (
			<iframe
				{...this.props.iframeProps}
				onLoad={this.onLoadHandler}
				ref={this.iframeRef}
				src={this.state.src}
				title={this.props.title}
			/>
		);
	}
}
