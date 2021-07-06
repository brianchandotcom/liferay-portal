# Using liferay-learn:message Tags

You can link to resources, such as [Liferay Learn](https://learn.liferay.com) articles, using the `liferay-learn:message` tag. For example, the *Click to Chat* app links to the [Chatwoot](https://learn.liferay.com/dxp/latest/en/site-building/personalizing-site-experience/enabling-automated-live-chat-systems/getting-a-chat-provider-account-id/chatwoot.html) Liferay Learn article.

![The Click to Chat page links to the Chatwoot article.](./images/01.png)

Users can click on `liferay-learn:message` tag generated links, like the one above, to get help.

The links have two parts:

1. A JSON file that specifies the resource you're linking to.
1. A `liferay-learn:message` tag that references the JSON file and one of its resources.

Specifying resources in a JSON file separate from the JSP facilitates adding locale translations and updating link labels and URLs.

**Note:** If a `liferay-learn:message` tag references a missing JSON file or unspecified resource entry, there's no ugly error--the tag simply doesn't render anything.

Start with specifying a resource.

## Adding Resources in a JSON File

Here are the steps:

1. In this folder (i.e., `learn-resources`), create a JSON file named after the module you're embedding links in.

1. Create an element for each resource you're linking to. For example, the [`learn-resources/marketplace-store-web.json`](https://github.com/liferay/liferay-portal/blob/master/learn-resources/marketplace-store-web.json) file has these resource entries:

	```json
	{
		"download-app": { // Resource key
			"en_US": {
				"message": "How can I download an app?", // Link label
				"url": "https://learn.liferay.com/dxp/latest/en/system-administration/installing-and-managing-apps/installing-apps/downloading-apps.html" // Resource URL
			}
		},
		"purchase-app": {
			"en_US": {
				"message": "How can I purchase an app?",
				"url": "https://learn.liferay.com/dxp/latest/en/system-administration/installing-and-managing-apps/getting-started/using-marketplace.html"
			}
		}
	}
	```

The example resource entries have the keys `download-app` and `purchase-app`. The keys are unique within the JSON file. You can provide each resource in multiple locales. For example, the resources above are in the `en_US` locale. For each locale, assign the `url` to the resource location and the `message` to a label for the resource link.

## Adding `liferay-learn:message` Tags to a JSP

In your module's JSP, link to the resources using `liferay-learn:message` tags. For example, the `marketplace-store-web` module's `view.jsp` file can reference the `learn-resources/marketplace-store-web.json` file's `download-app` resource with this code:

```javascript
<%@taglib uri="http://liferay.com/tld/learn" prefix="liferay-learn" %>

<liferay-learn:message
    key="download-app"
    resource="marketplace-store-web"
/>
```

The first line above includes the `liferay-learn` tag library. The `liferay-learn:message` tag links to the `download-app` resource in the `learn-resources/marketplace-store-web.json` file. When the JSP renders, the text *How can I download an app?* links to the resource located at <https://learn.liferay.com/dxp/latest/en/system-administration/installing-and-managing-apps/installing-apps/downloading-apps.html>.

That's how you link to Liferay Learn resources!

> A CDN server hosts the JSON files. For example, here's how the `<liferay-learn:messag key="download-app" resource="marketplace-store-web" />` tag works:
>
> 1. The tag checks for the resource file (JSON file with prefix `marketplace-store-web`) on the *local* CDN server at <https://learn-resources.liferay.com/marketplace-store-web.json>.
> 1. The local server checks the *global* server at <http://s3.amazonaws.com/learn-resources.liferay.com/marketplace-store-web.json> for updates to the resource.
> 1. If the local resource is valid, it's served immediately. Otherwise, the local server serves the resource after refreshing the local resource cache with the latest update from the global server.
>
> Note: The cache refreshes every four hours by default, per the [`learn.resources.refresh.time` portal property](../portal-impl/src/portal.properties).

## Guidelines

Here are some guidelines for writing the JSON files and tags.

### Name the JSON Files After the Web Modules That Will Use the Resources

For example, if want the `foo-web` module's JSPs to link to resources, create the resources in a JSON file called `liferay-resources/foo-web.json`.

### Make Resource Keys Unique Per JSON File

Don't duplicate resource keys in the same JSON file.

### Name Lone Resource Keys `general`

If a JSON file has only one resource key, name the key `general`.