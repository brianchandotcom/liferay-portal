/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseHttpRequest } from './core/BaseHttpRequest';
import type { OpenAPIConfig } from './core/OpenAPI';
import { FetchHttpRequest } from './core/FetchHttpRequest';
import { BlogPostingService } from './services/BlogPostingService';
import { BlogPostingImageService } from './services/BlogPostingImageService';
import { CommentService } from './services/CommentService';
import { ContentElementService } from './services/ContentElementService';
import { ContentSetElementService } from './services/ContentSetElementService';
import { ContentStructureService } from './services/ContentStructureService';
import { ContentTemplateService } from './services/ContentTemplateService';
import { DocumentService } from './services/DocumentService';
import { DocumentFolderService } from './services/DocumentFolderService';
import { KnowledgeBaseArticleService } from './services/KnowledgeBaseArticleService';
import { KnowledgeBaseAttachmentService } from './services/KnowledgeBaseAttachmentService';
import { KnowledgeBaseFolderService } from './services/KnowledgeBaseFolderService';
import { LanguageService } from './services/LanguageService';
import { MessageBoardAttachmentService } from './services/MessageBoardAttachmentService';
import { MessageBoardMessageService } from './services/MessageBoardMessageService';
import { MessageBoardSectionService } from './services/MessageBoardSectionService';
import { MessageBoardThreadService } from './services/MessageBoardThreadService';
import { NavigationMenuService } from './services/NavigationMenuService';
import { SitePageService } from './services/SitePageService';
import { StructuredContentService } from './services/StructuredContentService';
import { StructuredContentFolderService } from './services/StructuredContentFolderService';
import { WikiNodeService } from './services/WikiNodeService';
import { WikiPageService } from './services/WikiPageService';
import { WikiPageAttachmentService } from './services/WikiPageAttachmentService';
type HttpRequestConstructor = new (config: OpenAPIConfig) => BaseHttpRequest;
export class HeadlessDeliveryClient {
    public readonly blogPosting: BlogPostingService;
    public readonly blogPostingImage: BlogPostingImageService;
    public readonly comment: CommentService;
    public readonly contentElement: ContentElementService;
    public readonly contentSetElement: ContentSetElementService;
    public readonly contentStructure: ContentStructureService;
    public readonly contentTemplate: ContentTemplateService;
    public readonly document: DocumentService;
    public readonly documentFolder: DocumentFolderService;
    public readonly knowledgeBaseArticle: KnowledgeBaseArticleService;
    public readonly knowledgeBaseAttachment: KnowledgeBaseAttachmentService;
    public readonly knowledgeBaseFolder: KnowledgeBaseFolderService;
    public readonly language: LanguageService;
    public readonly messageBoardAttachment: MessageBoardAttachmentService;
    public readonly messageBoardMessage: MessageBoardMessageService;
    public readonly messageBoardSection: MessageBoardSectionService;
    public readonly messageBoardThread: MessageBoardThreadService;
    public readonly navigationMenu: NavigationMenuService;
    public readonly sitePage: SitePageService;
    public readonly structuredContent: StructuredContentService;
    public readonly structuredContentFolder: StructuredContentFolderService;
    public readonly wikiNode: WikiNodeService;
    public readonly wikiPage: WikiPageService;
    public readonly wikiPageAttachment: WikiPageAttachmentService;
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
        this.blogPosting = new BlogPostingService(this.request);
        this.blogPostingImage = new BlogPostingImageService(this.request);
        this.comment = new CommentService(this.request);
        this.contentElement = new ContentElementService(this.request);
        this.contentSetElement = new ContentSetElementService(this.request);
        this.contentStructure = new ContentStructureService(this.request);
        this.contentTemplate = new ContentTemplateService(this.request);
        this.document = new DocumentService(this.request);
        this.documentFolder = new DocumentFolderService(this.request);
        this.knowledgeBaseArticle = new KnowledgeBaseArticleService(this.request);
        this.knowledgeBaseAttachment = new KnowledgeBaseAttachmentService(this.request);
        this.knowledgeBaseFolder = new KnowledgeBaseFolderService(this.request);
        this.language = new LanguageService(this.request);
        this.messageBoardAttachment = new MessageBoardAttachmentService(this.request);
        this.messageBoardMessage = new MessageBoardMessageService(this.request);
        this.messageBoardSection = new MessageBoardSectionService(this.request);
        this.messageBoardThread = new MessageBoardThreadService(this.request);
        this.navigationMenu = new NavigationMenuService(this.request);
        this.sitePage = new SitePageService(this.request);
        this.structuredContent = new StructuredContentService(this.request);
        this.structuredContentFolder = new StructuredContentFolderService(this.request);
        this.wikiNode = new WikiNodeService(this.request);
        this.wikiPage = new WikiPageService(this.request);
        this.wikiPageAttachment = new WikiPageAttachmentService(this.request);
    }
}

