# Liferay Headless Commerce - Next.js Sample

This is a [Next.js](https://nextjs.org) made to consume [Liferay](https://www.liferay.com/)'s Headless Commerce APIs.

## 📋 Prerequisites

Before running this application, ensure you have:

- Node.js 18+ installed
- A running Liferay DXP instance with Commerce enabled
- Access to Liferay's Headless Commerce APIs

## 🏎️ Getting Started

First, install the dependencies:

```bash
npm install
```

Then run the development server:

```bash
npm run dev
```

Open [http://localhost:3000](http://localhost:3000) with your browser to see the result.

You can start editing the page by modifying `app/page.tsx`. The page auto-updates as you edit the file.

## ⚙️ Configuration

### Liferay

You'll need to add the following rule for the existing `COMMERCE_DEFAULT` [Service Access Policy](https://learn.liferay.com/w/dxp/security-and-administration/security/securing-web-services/setting-service-access-policies):

- **Service Class:** `com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0.ProductResourceImpl`
- **Method Name:** `getChannelProductByFriendlyUrlPath`

### Application Environment Vars

- `LIFERAY_CHANNEL_ID`: Your Liferay Commerce Channel ID.
- `LIFERAY_HOST`: Your Liferay instance URL (usually `http://localhost:8080` for local development).
- `NEXT_PUBLIC_SITE_NAME`: Your site name, e.g.: `Minium`.

## 📚 Learn More

- [Foundations of Liferay Headless APIs](https://learn.liferay.com/l/29393515)
- [Mastering Consuming Liferay Headless APIs](https://learn.liferay.com/l/29852017)
- [Liferay Headless Commerce](https://learn.liferay.com/dxp/latest/en/headless-delivery/consuming-apis/headless-commerce.html) - Liferay's headless commerce documentation
- [Learn Next.js](https://nextjs.org/learn)