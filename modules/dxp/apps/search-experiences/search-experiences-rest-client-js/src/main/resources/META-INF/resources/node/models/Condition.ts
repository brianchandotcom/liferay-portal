/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type Condition = {
    in?: {
        parameterName?: string;
        value?: Record<string, any>;
    };
    allConditions?: Array<Condition>;
    anyConditions?: Array<Condition>;
    contains?: {
        parameterName?: string;
        value?: Record<string, any>;
    };
    equals?: {
        format?: string;
        parameterName?: string;
        value?: Record<string, any>;
    };
    exists?: {
        parameterName?: string;
    };
    not?: Condition;
    range?: {
        format?: string;
        gt?: Record<string, any>;
        gte?: Record<string, any>;
        lt?: Record<string, any>;
        lte?: Record<string, any>;
        parameterName?: string;
    };
};

