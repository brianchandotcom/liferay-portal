# Blueprints Bulk Loader

Creates Liferay Journal Articles from Google Places data or Wikipedia articles.

**_This modules is for internal testing purposes only._**

## Importing Google Places Data

Imports location data imported using the Google "Places API". Adds also geopoints to an expando field "location".

Additional data sets can be added by:

1. Copying the results of a "Places API" request into a .json file
2. Placing the .json file in the resources directory
3. Putting an entry in ``FILENAME_TO_CITY_MAP`` in ``PlacesConstants`` class.

An API Key is needed to perform requests, see https://liferay.slack.com/archives/C0154CEGR3Q/p1598901770005800

### Example "Places API" requests

* Restaurants within ~10 miles of Los Angeles:
https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=$API_KEY&location=34.061645,-118.261353&radius=15000&type=restaurant

* Tourist Attractions within ~1 mile of New York:
https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=$API_KEY&location=40.761619,-73.972851&radius=1500&type=tourist_attraction

JSON results were formatted with https://jsonformatter.curiousconcept.com/

### List of supported "types" for a "Places API" request
https://developers.google.com/places/web-service/supported_types

## Importing Wikipedia Articles

Crawling starts from the given Wikipedia article(s) following the "links" in article metadata.

Please note, that import process takes time: importing hundreds of articles can take several minutes.