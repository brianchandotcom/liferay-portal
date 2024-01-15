/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {Captcha_v1_0_Captcha} from '../models/Captcha_v1_0_Captcha';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class CaptchaV10CaptchaService {

	/**
	 * @returns Captcha_v1_0_Captcha default response
	 * @throws ApiError
	 */
	public static captchaV10GetCaptchaChallenge(): CancelablePromise<Captcha_v1_0_Captcha> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/captcha/v1.0/captcha/challenge',
		});
	}

	/**
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static captchaV10PostCaptchaResponse(
		requestBody?: Captcha_v1_0_Captcha
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/captcha/v1.0/captcha/response',
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
