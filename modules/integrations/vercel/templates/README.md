# 📚 **Liferay Headless Templates: Next.js Frontends**

This project organizes three distinct Next.js applications, all of which consume data directly from **Liferay Headless APIs**. Each project serves a specific business function and resides in its own dedicated directory.

> [!NOTE]
> 💡 For **project-specific setup** (e.g., required environment variables, local run commands, and individual build steps), please navigate to the corresponding project directory and consult **its dedicated README.md file.**

## **Blog**

This application provides the main **blog experience**, pulling articles, categorization, and metadata using Liferay's **Content Management System (CMS) APIs**.

-   **Directory:** [./blog](./blog/)
-   **Purpose:** Article listing and individual post view.

## **Commerce**

This application is dedicated to the **product discovery experience**, including the main catalog and detailed product display pages. It fetches data from Liferay's dedicated **Commerce APIs**.

-   **Directory:** [./commerce](./commerce/)
-   **Purpose:** Product browsing, filtering, and detailed viewing.

## **Content Page**

This application manages **informational pages** (like "Event Details", "About Us" or "Terms"), utilizing dynamic data fetched via Liferay's **Structured Content**.

-   **Directory:** [./content-page](./content-page/)
-   **Purpose:** Displaying various content types, dynamic page layouts, taking advantage of Liferay's localization features.

## **🛠️ Technology Stack**

| Technology                | Role                                                         |
| :------------------------ | :----------------------------------------------------------- |
| **Next.js**               | The React framework used for server-side rendering (SSR).    |
| **Liferay Headless APIs** | The single source of truth for all content and product data. |
