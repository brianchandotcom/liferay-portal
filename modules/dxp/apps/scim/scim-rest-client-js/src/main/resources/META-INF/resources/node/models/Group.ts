/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseScim } from './BaseScim';
import type { MultiValuedAttribute } from './MultiValuedAttribute';
export type Group = (BaseScim & {
    /**
     * A human-readable name for the Group.
     */
    displayName?: string;
    /**
     * A list of members of the Group.
     */
    members?: Array<MultiValuedAttribute>;
});

