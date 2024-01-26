/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ObjectField } from './ObjectField';
export type ObjectRelationship = {
    readonly actions?: Record<string, Record<string, string>>;
    deletionType?: 'cascade' | 'disassociate' | 'prevent';
    edge?: boolean;
    externalReferenceCode?: string;
    readonly id?: number;
    label?: Record<string, string>;
    name?: string;
    objectDefinitionExternalReferenceCode1?: string;
    objectDefinitionExternalReferenceCode2?: string;
    objectDefinitionId1?: number;
    objectDefinitionId2?: number;
    objectDefinitionModifiable2?: boolean;
    objectDefinitionName2?: string;
    objectDefinitionSystem2?: boolean;
    objectField?: ObjectField;
    parameterObjectFieldId?: number;
    parameterObjectFieldName?: string;
    readonly reverse?: boolean;
    system?: boolean;
    type?: 'oneToMany' | 'oneToOne' | 'manyToMany';
};

