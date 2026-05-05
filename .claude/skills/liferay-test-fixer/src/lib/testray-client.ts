import { TESTRAY_CLIENT_ID, TESTRAY_CLIENT_SECRET } from '@/lib/env'
import { TestrayException } from '@/lib/testray-exception'

type QueryParams = Record<string, string | number | undefined>

let accessToken: string | null = null
let accessTokenRequest: Promise<string> | null = null
let expiresAt = 0

function isTokenValid() {
	if (!accessToken) {
		return false
	}

	return Date.now() < expiresAt
}

async function requestAccessToken(): Promise<string> {
	const clientId = TESTRAY_CLIENT_ID
	const clientSecret = TESTRAY_CLIENT_SECRET

	if (!clientId || !clientSecret) {
		throw new TestrayException({
			message: 'TESTRAY_CLIENT_ID and TESTRAY_CLIENT_SECRET are required',
			status: 500,
		})
	}

	const response = await fetch('https://testray.liferay.com/o/oauth2/token', {
		method: 'POST',
		headers: {
			'Authorization': `Basic ${btoa(`${clientId}:${clientSecret}`)}`,
			'Content-Type': 'application/x-www-form-urlencoded',
		},
		body: new URLSearchParams({
			grant_type: 'client_credentials',
		}),
	})

	if (!response.ok) {
		throw new TestrayException({
			message: 'Unable to get access token',
			status: response.status,
		})
	}

	const { access_token, expires_in } = (await response.json()) as {
		access_token?: string
		expires_in?: number
	}

	if (!access_token) {
		throw new TestrayException({
			message: 'OAuth response does not include access_token',
			status: 502,
		})
	}

	accessToken = access_token

	const expiresInMs = Math.max(1, expires_in ?? 300) * 1000

	expiresAt = Date.now() + expiresInMs - 30_000

	return access_token
}

async function getAccessToken(): Promise<string> {
	if (isTokenValid()) {
		return accessToken as string
	}

	if (accessTokenRequest) {
		return accessTokenRequest
	}

	accessTokenRequest = requestAccessToken()

	try {
		return await accessTokenRequest
	} finally {
		accessTokenRequest = null
	}
}

async function getHeaders(): Promise<Record<string, string>> {
	return {
		Authorization: `Bearer ${await getAccessToken()}`,
		Accept: 'application/json',
	}
}

async function get<T>({
	url,
	params = {},
}: {
	url: string
	params?: QueryParams
}) {
	const fetchUrl = new URL(url)

	for (const [key, value] of Object.entries(params)) {
		if (value !== undefined) {
			fetchUrl.searchParams.set(key, String(value))
		}
	}

	const response = await fetch(fetchUrl.toString(), {
		method: 'GET',
		headers: await getHeaders(),
	})

	if (!response.ok) {
		throw new TestrayException({
			message: url,
			status: response.status,
		})
	}

	const json = (await response.json()) as Record<string, unknown>

	if ('items' in json) {
		return json.items as T
	}

	return json as T
}

export const TestrayClient = {
	get,
}
