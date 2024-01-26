/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { FetchHttpRequest } from './core/FetchHttpRequest';
import { KeywordService } from './services/KeywordService';
import { SelectionService } from './services/SelectionService';
import { StatusService } from './services/StatusService';
import { TaxonomyCategoryService } from './services/TaxonomyCategoryService';
import { TaxonomyVocabularyService } from './services/TaxonomyVocabularyService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class BulkRestClient {
    public readonly keyword: KeywordService;
    public readonly selection: SelectionService;
    public readonly status: StatusService;
    public readonly taxonomyCategory: TaxonomyCategoryService;
    public readonly taxonomyVocabulary: TaxonomyVocabularyService;
    public readonly request: BaseHttpRequest;
    constructor(config?: Partial<OpenAPIConfig>, HttpRequest: HttpRequestConstructor = FetchHttpRequest) {
        this.request = new HttpRequest({
            BASE: config?.BASE ?? '',
            VERSION: config?.VERSION ?? '1.0',
            WITH_CREDENTIALS: config?.WITH_CREDENTIALS ?? false,
            CREDENTIALS: config?.CREDENTIALS ?? 'include',
            TOKEN: config?.TOKEN,
            USERNAME: config?.USERNAME,
            PASSWORD: config?.PASSWORD,
            HEADERS: config?.HEADERS,
            ENCODE_PATH: config?.ENCODE_PATH,
        });
        this.keyword = new KeywordService(this.request);
        this.selection = new SelectionService(this.request);
        this.status = new StatusService(this.request);
        this.taxonomyCategory = new TaxonomyCategoryService(this.request);
        this.taxonomyVocabulary = new TaxonomyVocabularyService(this.request);
    }
}

