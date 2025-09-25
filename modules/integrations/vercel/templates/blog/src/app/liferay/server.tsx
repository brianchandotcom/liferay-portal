const LIFERAY_HOST = process.env.LIFERAY_HOST || "";
const LIFERAY_HEADLESS_BASE_URL = `${LIFERAY_HOST}/o`;
const LIFERAY_SPACE_ID = process.env.LIFERAY_SPACE_ID || 0;

export const liferay = {
  fetch: async (
    resource: string | URL | globalThis.Request,
    init?: RequestInit
  ) => {
    const endpoint =
      typeof resource === "string" && resource.startsWith("/")
        ? `${LIFERAY_HEADLESS_BASE_URL}${resource}`
        : resource;

    const response = await fetch(endpoint, {
      method: init?.method || "GET",
      headers: {
        accept: "*/*",
        "accept-language": "en-US,en;q=0.5",
        "content-type": "application/json",
        ...init?.headers,
      },
      next: {
        revalidate: 3600,
      },
    });

    return response;
  },

  getSpace: () => {
    return {
      id: Number(LIFERAY_SPACE_ID),
    };
  },

  getDocument: (documentPath: string) => {
    if (documentPath.startsWith("/")) {
      return `${LIFERAY_HOST}${documentPath}`;
    }

    return documentPath;
  },

  cmsEndpoints: {
    blogPosts: ({
      spaceId,
      page = 1,
      pageSize,
      sort,
    }: {
      spaceId: number;
      page: number;
      pageSize: number;
      sort: string;
    }) => {
      return `/cms/blogs/scopes/${spaceId}?page=${page}&pageSize=${pageSize}&sort=${sort}`;
    },

    blogPost: ({ blogId }: { blogId: number }) => {
      return `/cms/blogs/${blogId}`;
    },
  },
};

export type Liferay = typeof liferay;

export type WithLiferay<TParams = unknown> = TParams & { liferay: Liferay };
