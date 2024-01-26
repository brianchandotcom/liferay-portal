/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BlogPostingImage } from '../models/BlogPostingImage';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class BlogPostingImageService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes the blog post's image.
     * @returns void
     * @throws ApiError
     */
    public deleteBlogPostingImage({
        blogPostingImageId,
    }: {
        blogPostingImageId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-delivery/v1.0/blog-posting-images/{blogPostingImageId}',
            path: {
                'blogPostingImageId': blogPostingImageId,
            },
        });
    }
    /**
     * Retrieves the blog post's image. The binary image is returned as a relative URL to the image itself.
     * @returns BlogPostingImage
     * @throws ApiError
     */
    public getBlogPostingImage({
        blogPostingImageId,
        fields,
        nestedFields,
        restrictFields,
    }: {
        blogPostingImageId: number,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
    }): CancelablePromise<BlogPostingImage> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/blog-posting-images/{blogPostingImageId}',
            path: {
                'blogPostingImageId': blogPostingImageId,
            },
            query: {
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
            },
        });
    }
    /**
     * Retrieves the site's blog post images. Results can be paginated, filtered, searched, and sorted.
     * @returns BlogPostingImage
     * @throws ApiError
     */
    public getSiteBlogPostingImagesPage({
        siteId,
        aggregationTerms,
        fields,
        nestedFields,
        restrictFields,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        siteId: number,
        aggregationTerms?: Array<string>,
        fields?: string,
        nestedFields?: string,
        restrictFields?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<BlogPostingImage>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-delivery/v1.0/sites/{siteId}/blog-posting-images',
            path: {
                'siteId': siteId,
            },
            query: {
                'aggregationTerms': aggregationTerms,
                'fields': fields,
                'nestedFields': nestedFields,
                'restrictFields': restrictFields,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * Creates a blog post image. The request body must be `multipart/form-data` with two parts, the file's bytes (`file`), and an optional JSON string (`blogPostingImage`) with the metadata.
     * @returns BlogPostingImage
     * @throws ApiError
     */
    public postSiteBlogPostingImage({
        siteId,
        formData,
    }: {
        siteId: number,
        formData?: {
            blogPostingImage?: BlogPostingImage;
            file?: Blob;
        },
    }): CancelablePromise<BlogPostingImage> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-delivery/v1.0/sites/{siteId}/blog-posting-images',
            path: {
                'siteId': siteId,
            },
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }
}
