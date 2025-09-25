import { createClient } from "liferay-headless-rest-client";

const LIFERAY_HOST = process.env.LIFERAY_HOST || "";
const LIFERAY_CHANNEL_ID = process.env.LIFERAY_CHANNEL_ID || "0";
const LIFERAY_SITE_NAME =
  process.env.NEXT_PUBLIC_SITE_NAME || "Liferay Commerce";

export const liferay = {
  client: createClient({
    baseUrl: LIFERAY_HOST,
  }),

  getChannel: () => {
    return {
      id: LIFERAY_CHANNEL_ID,
      siteName: LIFERAY_SITE_NAME,
    };
  },
};

export type Liferay = typeof liferay;

export type WithLiferay<TParams = unknown> = TParams & { liferay: Liferay };
