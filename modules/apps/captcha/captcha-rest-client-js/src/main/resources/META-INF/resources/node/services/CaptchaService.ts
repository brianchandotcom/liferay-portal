/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Captcha } from '../models/Captcha';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class CaptchaService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Captcha Send a captcha token and image.
     * @throws ApiError
     */
    public getCaptchaChallenge(): CancelablePromise<Captcha> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/captcha/v1.0/captcha/challenge',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public postCaptchaResponse({
        requestBody,
    }: {
        requestBody?: Captcha,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/captcha/v1.0/captcha/response',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Invalid answer provided`,
            },
        });
    }
}
