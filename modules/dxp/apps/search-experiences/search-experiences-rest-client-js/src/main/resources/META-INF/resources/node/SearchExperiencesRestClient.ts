/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { NodeHttpRequest } from './core/NodeHttpRequest';
import { EmbeddingProviderValidationResultService } from './services/EmbeddingProviderValidationResultService';
import { FieldMappingInfoService } from './services/FieldMappingInfoService';
import { KeywordQueryContributorService } from './services/KeywordQueryContributorService';
import { MlModelService } from './services/MlModelService';
import { ModelPrefilterContributorService } from './services/ModelPrefilterContributorService';
import { QueryPrefilterContributorService } from './services/QueryPrefilterContributorService';
import { SearchableAssetNameService } from './services/SearchableAssetNameService';
import { SearchableAssetNameDisplayService } from './services/SearchableAssetNameDisplayService';
import { SearchIndexService } from './services/SearchIndexService';
import { SearchResponseService } from './services/SearchResponseService';
import { SxpBlueprintService } from './services/SxpBlueprintService';
import { SxpElementService } from './services/SxpElementService';
import { SxpParameterContributorDefinitionService } from './services/SxpParameterContributorDefinitionService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class SearchExperiencesRestClient {
    public readonly embeddingProviderValidationResult: EmbeddingProviderValidationResultService;
    public readonly fieldMappingInfo: FieldMappingInfoService;
    public readonly keywordQueryContributor: KeywordQueryContributorService;
    public readonly mlModel: MlModelService;
    public readonly modelPrefilterContributor: ModelPrefilterContributorService;
    public readonly queryPrefilterContributor: QueryPrefilterContributorService;
    public readonly searchableAssetName: SearchableAssetNameService;
    public readonly searchableAssetNameDisplay: SearchableAssetNameDisplayService;
    public readonly searchIndex: SearchIndexService;
    public readonly searchResponse: SearchResponseService;
    public readonly sxpBlueprint: SxpBlueprintService;
    public readonly sxpElement: SxpElementService;
    public readonly sxpParameterContributorDefinition: SxpParameterContributorDefinitionService;
    public readonly request: BaseHttpRequest;
    constructor(config?: Partial<OpenAPIConfig>, HttpRequest: HttpRequestConstructor = NodeHttpRequest) {
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
        this.embeddingProviderValidationResult = new EmbeddingProviderValidationResultService(this.request);
        this.fieldMappingInfo = new FieldMappingInfoService(this.request);
        this.keywordQueryContributor = new KeywordQueryContributorService(this.request);
        this.mlModel = new MlModelService(this.request);
        this.modelPrefilterContributor = new ModelPrefilterContributorService(this.request);
        this.queryPrefilterContributor = new QueryPrefilterContributorService(this.request);
        this.searchableAssetName = new SearchableAssetNameService(this.request);
        this.searchableAssetNameDisplay = new SearchableAssetNameDisplayService(this.request);
        this.searchIndex = new SearchIndexService(this.request);
        this.searchResponse = new SearchResponseService(this.request);
        this.sxpBlueprint = new SxpBlueprintService(this.request);
        this.sxpElement = new SxpElementService(this.request);
        this.sxpParameterContributorDefinition = new SxpParameterContributorDefinitionService(this.request);
    }
}

