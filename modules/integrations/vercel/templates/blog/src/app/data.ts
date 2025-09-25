import type { WithLiferay, Page } from "@/app/liferay";

export interface CMSBlogPosting {
  id: number;
  content: string;
  contentRawText: string;
  title: string;
  keywords: string[];
  creator: {
    name: string;
  };
  dateCreated: string;
  coverImage: {
    id: number;
    link: {
      href: string;
      label: string;
    };
  };
  friendlyUrlPath: string;
}

export const getCMSBlogPostings = async ({
  liferay,
  page,
}: WithLiferay<{ page: number }>) => {
  try {
    const liferaySpace = liferay.getSpace();
    const response = await liferay.fetch(
      liferay.cmsEndpoints.blogPosts({
        page,
        pageSize: 21,
        spaceId: liferaySpace.id,
        sort: "dateCreated:desc",
      })
    );

    const data = (await response.json()) as Page<CMSBlogPosting>;

    return { data };
  } catch (error) {
    return { error, data: null };
  }
};
