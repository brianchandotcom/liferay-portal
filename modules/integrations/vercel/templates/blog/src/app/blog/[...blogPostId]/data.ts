import type { WithLiferay } from "@/app/liferay";

export interface CMSBlogPosting {
  id: number;
  content: string;
  contentRawText: string;
  title: string;
  subtitle: string;
  keywords: string[];
  dateCreated: string;
  coverImage: {
    id: number;
    link: {
      href: string;
      label: string;
    };
  };
  creator: {
    additionalName: string;
    contentType: string;
    externalReferenceCode: string;
    familyName: string;
    givenName: string;
    id: number;
    name: string;
  };
  friendlyUrlPath: string;
}

export const getCMSBlogPosting = async ({
  blogId,
  liferay,
}: WithLiferay<{ blogId: number }>) => {
  try {
    const response = await liferay.fetch(
      liferay.cmsEndpoints.blogPost({ blogId })
    );

    return { data: (await response.json()) as CMSBlogPosting };
  } catch (error) {
    return { error, data: null };
  }
};
