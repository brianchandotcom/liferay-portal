/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type FakeXMLHttpRequestResponse =
	| {body: unknown; status: number}
	| 'networkError';

export class FakeXMLHttpRequest {
	static current: FakeXMLHttpRequest;
	static defaultResponse?: FakeXMLHttpRequestResponse;
	static requests: FakeXMLHttpRequest[] = [];
	static responses: FakeXMLHttpRequestResponse[] = [];

	body?: Blob;
	headers: Record<string, string> = {};
	method?: string;
	onerror?: () => void;
	onload?: () => void;
	responseText = '';
	status = 0;
	upload: {onprogress?: (event: Partial<ProgressEvent>) => void} = {};
	url?: string;

	static reset() {
		FakeXMLHttpRequest.defaultResponse = undefined;
		FakeXMLHttpRequest.requests = [];
		FakeXMLHttpRequest.responses = [];
	}

	abort() {}

	open(method: string, url: string) {
		this.method = method;
		this.url = url;
	}

	respond(status: number, body: unknown) {
		this.responseText = JSON.stringify(body);
		this.status = status;

		this.onload!();
	}

	send(body: Blob) {
		this.body = body;

		FakeXMLHttpRequest.current = this;
		FakeXMLHttpRequest.requests.push(this);

		const response =
			FakeXMLHttpRequest.responses.shift() ??
			FakeXMLHttpRequest.defaultResponse;

		if (!response) {
			return;
		}

		setTimeout(() => {
			if (response === 'networkError') {
				this.onerror!();

				return;
			}

			this.upload.onprogress?.({loaded: body.size});

			this.respond(response.status, response.body);
		});
	}

	setRequestHeader(key: string, value: string) {
		this.headers[key] = value;
	}
}
