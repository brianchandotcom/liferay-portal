/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Multi-valued attributes contain a list of elements using the JSON array format.
 */
export type MultiValuedAttribute = {
    /**
     * The reference URI of a target resource, if the attribute is a reference.
     */
    $ref?: string;
    /**
     * A human-readable name, primarily used for display purposes and having a mutability of "immutable".
     */
    display?: string;
    /**
     * Boolean value indicating the 'primary' or preferred attribute value for this attribute, e.g., the preferred mailing address or the primary email address.
     */
    primary?: boolean;
    /**
     * A label indicating the attribute's function, e.g., "work" or "home".
     */
    type?: string;
    /**
     * The attribute's significant value, e.g., email address, phone number.
     */
    value?: string;
};

