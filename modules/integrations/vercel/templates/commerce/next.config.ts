import type { NextConfig } from "next";

const imageDomain = (process.env.LIFERAY_HOST ?? "")
  .replace("https://", "")
  .replace("http://", "");

const nextConfig: NextConfig = {
  images: {
    domains: [imageDomain],
  },
};

export default nextConfig;
