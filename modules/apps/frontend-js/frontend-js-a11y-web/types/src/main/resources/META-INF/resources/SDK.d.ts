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
declare class Channel<T, S> {
	#private;
	constructor(onRecv: Recv<T, S>, side: boolean);
	tx<P, K>(target: Window, payload: P, kind?: K): void;
	rx(event: MessageEvent<ChannelEvent<T, S>>): void;
}
export declare class SDK<T, S> {
	channel: Channel<T, S>;
	constructor(onRecv: Recv<T, S>, side: boolean);
	observe(): void;
	unobserve(): void;
}
export {};
