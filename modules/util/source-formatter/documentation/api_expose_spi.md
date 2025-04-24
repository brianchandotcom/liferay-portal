## API Expose SPI

Exported types in API modules should not expose SPI types at signature level.
Once a SPI type is exposed via an API type, it is practically promoted to become an API.

You should consider:
* Move the exposed SPI types to API modules to make them formal APIs
* Make a cleaner separation to prevent SPI types from being exposed by APIs