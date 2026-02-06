# Liferay Headless Commerce - Next.js Sample

This [Next.js](https://nextjs.org) template consumes [Liferay's](https://www.liferay.com/) Commerce headless APIs. For more information, read [Getting Started with Liferay](https://learn.liferay.com/w/dxp/getting-started).

 ## Prerequisites

- Git
- Node.js 22+
- Liferay Portal 2025.Q4+

## Clone the Template

1. Run this command:

   ```bash
   curl -sL https://raw.githubusercontent.com/liferay/liferay-portal/master/modules/integrations/vercel/clone_template.sh | bash -s -- commerce
   ```

1. Navigate to the repository directory:

   ```bash
   cd commerce
   ```

## Set Up Your Local Liferay Instance

1. Go to your running Liferay instance [http://localhost:8080](http://localhost:8080).

1. Create a Commerce site, following [this guide](https://learn.liferay.com/w/dxp/commerce/starting-a-store/accelerators).

1. Select the `Minium` template and give it a name.

1. Click *Save*.

## Add the Service Access Policy

Liferay restricts API access by default for security. You must configure a [Service Access Policy](https://learn.liferay.com/w/dxp/security-and-administration/security/securing-web-services/setting-service-access-policies) to allow access to the necessary endpoints.

1. Navigate to Control Panel &rarr; Security &rarr; Service Access Policies.

1. Click the *COMMERCE_DEFAULT* policy.

1. In the Allowed Service Signatures section, add a new row with the following values:
   - **Service Class:** `com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0.ProductResourceImpl`
   - **Method Name:** `getChannelProductByFriendlyUrlPath`

1. Click *Save*.

## Run Your Template

To get your template up and running, first, install the dependencies:

```bash
npm install
```

Before starting it, define your environment variables.

1. Configure your environment variables:

   ```bash
   cp .env.example .env
   ```

1. Open `.env` and define the following keys:

   - `LIFERAY_HOST`: your Liferay instance URL (`http://localhost:8080` for local development)
   - `LIFERAY_CHANNEL_ID`: your Liferay Commerce Channel ID
   - `NEXT_PUBLIC_SITE_NAME`: your site name (e.g., `Minium`)

1. Run the development server:

   ```bash
   npm run dev
   ```

1. Open [http://localhost:3000](http://localhost:3000) in your browser.

You can now edit `app/page.tsx` to modify the page. The application auto-updates as you edit the file.

## Learn More

- [Foundations of Liferay Headless APIs](https://learn.liferay.com/l/29393515)
- [Mastering Consuming Liferay Headless APIs](https://learn.liferay.com/l/29852017)
- [Liferay Headless Commerce](https://learn.liferay.com/dxp/latest/en/headless-delivery/consuming-apis/headless-commerce.html)
- [Learn Next.js](https://nextjs.org/learn)