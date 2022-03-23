# Analytics Clients

## Application IDs

An `applicationId` identifies a group of events related to a specific
application. For example, a Blog app might send events like `blogClicked`,
`blogViewed`, `blogDepthReached`, etc. The Analytics server uses the
`applicationId` to find all those Blog-related events and associate them with
the Blog application. As a convention, all application IDs should start with an
uppercase letter.

## Events and Properties

Events are representations of actions performed by users. Events are composed by
an ID and their properties.

Every event is uniquely identified by its ID, which should be a string of
characters following the
[camel case](https://en.wikipedia.org/wiki/Camel_case) convention. The contents
of an ID should follow the *objectAction* pattern:

- *object* refers to what you are tracking (e.g., Form, Blog, Scroll).
- *Action* refers to the action the user performed (e.g., Focused, Viewed,
  Reached) on the *object*. The *Action* should be written in past tense.

Properties of an event are a map containing information about that particular
event. Keys of that map should also follow the
[camel case](https://en.wikipedia.org/wiki/Camel_case) convention.

### Blog Events

#### Application ID: `Blog`

| Object | Action | Event Id | Event Properties |
| ------ | ------ | -------- | ---------------- |
| `Blog` | Clicked | `blogClicked` | `entryId`, `href`, `text`, `src` |
| `Blog` | Depth Reached | `blogDepthReached` | `entryId`, `depth` |
| `Blog` | Viewed | `blogViewed` | `entryId`, `title` |

### Blog Event Properties

#### entryId: Long

The blog's unique ID.

#### href: String

The href of the blog's clicked link.

#### text: String

The text of the blog's clicked link.

#### src: String

The source of the blog's clicked image.

#### depth: Long

A number representing how far the user scrolled into the blog's contents.

#### title: String

An attribute to describe any kind of asset. This information is presented in the
analytics reports.

### Document Events

#### Application ID: `Document`

| Object | Action | Event Id | Event Properties |
| ------ | ------ | -------- | ---------------- |
| `Document` | Downloaded | `documentDownloaded` | `fileEntryId`, `fileEntryVersion`, `title` |
| `Document` | Previewed | `documentPreviewed` | `fileEntryId`, `fileEntryVersion` |

### Document Event Properties

#### fileEntryId: Long

The unique ID for the document.

#### fileEntryVersion: Long

The document's version.

#### title: String

An attribute to describe any kind of asset. This information is presented in the
analytics reports.

### Form Events

#### Application ID: `Form`

| Object | Action | Event Id | Event Properties |
| ------ | ------ | -------- | ---------------- |
| `Field` | Blurred | `fieldBlurred` | `fieldName`, `formId`, `focusDuration` |
| `Field` | Focused | `fieldFocused` | `fieldName`, `formId` |
| `Form` | Submitted | `formSubmitted` | `formId` |
| `Form` | Viewed | `formViewed` | `formId`, `title` |

### Form Event Properties

#### fieldName: String

The HTML field's name attribute.

#### formId: String

The form's identifier.

#### focusDuration: Long

The time elapsed since the field received focus.

#### title: String

An attribute to describe any kind of asset. This information is presented in the
analytics reports.

### Page Events

#### Application ID: `Page`

| Object | Action | Event Id | Event Properties |
| ------ | ------ | -------- | ---------------- |
| `Page` | Depth Reached | `pageDepthReached` | `depth` |
| `Page` | Loaded | `pageLoaded` | `pageLoadTime` |
| `Page` | Unloaded | `pageUnloaded` | `viewDuration` |

### Page Event Properties

#### depth: Long

A number representing how far the user scrolled into the page.

#### pageLoadTime: Long

A performance indicator for how long a page took to load.

#### viewDuration: Long

The time elapsed from when the page was loaded until the page was unloaded.

## JS Client

Paste this code inside the HTML head:

```html
<script>
(function(u, c, a, m, o,l){o="script",l=document,a=l.createElement(o)
,m=l.getElementsByTagName(o)[0],a.async=1,a.src=u,a.onload=c,
m.parentNode.insertBefore(a,m)})('https://analytics.liferay.com/analytics-all-min.js', function(){

    Analytics.create({ analyticsKey: 'MyAnalyticsKey' });
    Analytics.send('pageViewed', 'Page');
});
</script>
```

The `analyticsKey` is an identifier associated to your Liferay Portal account. The
identity of the user generating the events is automatically determined by the
Analytics Client and the Identify Service. You can manually provide its
identity, however, by calling the `Analytics` object's `setIdentity` method:

```html
    Analytics.create({ analyticsKey: 'MyAnalyticsKey' });
    Analytics.setIdentity({ email: 'foo@bar.com', name: 'Foo' });
```

You can track custom events by invoking the `Analytics` object's `send` method.
For example,

```html
    element.addEventListener('click', function(evt) {
        Analytics.send('share', 'Blog', { socialNetwork: 'twitter'});
    });
```

The first argument of the `send` method identifies the event (e.g., `share`) and
the second argument identifies the application associated to it (e.g., `Blog`).
You can pass extra information through the third argument (optional).

### Asset Information

To help the client gather more information about the assets on a page, it's helpful
to annotate the asset markup with some
[data attributes](https://www.w3.org/TR/2011/WD-html5-20110525/elements.html#embedding-custom-non-visible-data-with-the-data-attributes).

#### Supported data attributes

| Attribute | Data | Description |
| --------- | ---- | ----------- |
| `data-analytics-asset-id` | `id` | A unique identifier for the asset. |
| `data-analytics-asset-title` | `title` | A descriptive title for the asset. |
| `data-analytics-asset-type` | `type` | The asset type (File, Blog, Form, etc.). |