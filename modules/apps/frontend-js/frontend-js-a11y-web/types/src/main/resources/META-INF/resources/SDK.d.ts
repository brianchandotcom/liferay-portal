/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare const PROTOCOL = 'com.liferay.frontend.js.a11y.protocol';
export declare type ChannelEvent<T, K> = {
	kind: K;
	payload: T;
	protocol: typeof PROTOCOL;
};
export declare type Recv<T, S> = (payload: T, kind?: S) => void;

/**
 * The communication channel is responsible for implementing the tx and rx
 * methods, the channel is bidirectional so it will transmit (tx) and
 * receive (rx) messages through the implementation of a simple protocol to
 * differentiate messages on the same channel.
 */
declare class Channel<T, S> {
	#private;
	constructor(onRecv: Recv<T, S>, side: boolean);

	/**
	 * Transmit the event to the target following the communication protocol so
	 * that the other side can accept. Kind is the type of event so it can be
	 * differentiated in the receptor.
	 */
	tx<P, K>(target: Window, payload: P, kind?: K): void;

	/**
	 * Receive is not used directly but is used to verify the identity of
	 * the protocol and attached to the window listener, if it satisfies
	 * the condition it calls the callback.
	 */
	rx(event: MessageEvent<ChannelEvent<T, S>>): void;
}

/**
 * SDK is a simple abstraction to create side channels of communication between
 * the main window and the iframe the same happens inversely between the iframe
 * and the main window.
 */
export declare class SDK<T, S> {
	private channelRx;
	channel: Channel<T, S>;
	constructor(onRecv: Recv<T, S>, side: boolean);
	observe(): void;
	unobserve(): void;
}
export {};
