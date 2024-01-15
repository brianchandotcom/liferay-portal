/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DigitalSignatureRest_v1_0_DSDocument} from './DigitalSignatureRest_v1_0_DSDocument';
import type {DigitalSignatureRest_v1_0_DSRecipient} from './DigitalSignatureRest_v1_0_DSRecipient';
export type DigitalSignatureRest_v1_0_DSEnvelope = {
	readonly 'dateCreated'?: string;
	readonly 'dateModified'?: string;
	'dsDocument'?: Array<DigitalSignatureRest_v1_0_DSDocument>;
	'dsRecipient'?: Array<DigitalSignatureRest_v1_0_DSRecipient>;
	'emailBlurb'?: string;
	'emailSubject'?: string;
	'id'?: string;
	'name'?: string;
	'senderEmailAddress'?: string;
	'siteId'?: number;
	'status'?: string;
	readonly 'x-class-name'?: string;
};
