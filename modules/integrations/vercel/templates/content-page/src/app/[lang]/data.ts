import type { WithLiferay, LocalizedField } from "@/liferay";

export interface ContentData
  extends LocalizedField<"title">,
    LocalizedField<"content">,
    LocalizedField<"summary"> {
  id: number;
  dateTime: string;
  image: {
    link: { href: string; label: string };
  };
  locationMapUrl: string;
  locationName: string;
  registrationLink: string;
}

export const getContentData = async ({
  lang,
  liferay,
}: WithLiferay<{ lang: string }>): Promise<{
  error?: unknown;
  data: null | ContentData;
}> => {
  try {
    const response = await liferay.fetch(
      liferay.contentEndpoints.getContentURL(),
      { lang }
    );

    const data = (await response.json()) as ContentData;

    return { data };
  } catch (error) {
    return { error, data: null };
  }
};
