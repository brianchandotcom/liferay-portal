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

declare const PROTOCOL = 'com.liferay.frontend.js.a11y.protocol';
export declare type ChannelEvent<T, K> = {
	payload: T;
	kind: K;
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
