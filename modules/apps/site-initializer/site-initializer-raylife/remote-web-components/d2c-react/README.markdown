# Direct To Customer - Web Component

This repository contains a react project created with [Create React App](https://github.com/facebook/create-react-app), adapted to work with [Liferay's Remote Web Components](https://github.com/rotty3000/remote-web-component-poc). More examples [here](https://github.com/rotty3000/remote-component-test).

## How to setup

First of all, its necessary to install `package.json` dependencies. Inside the project root folder run:

```bash
npm install
# or
yarn install
```

### Requirement

React >= **17.0.2**
React-DOM >= **17.0.2**

### Development

Start the react application with:

```bash
npm start
# or
yarn start
```

The application will be available at `http://localhost:3000`

#### Testing locally with Mock Server

During development you can start a local server to mock the api calls. To do this run:

```bash
./mock/start.sh
```

The mock server will be available at `http://localhost:3333`

#### Testing locally with liferay

To deploy changes execute, every time you want to check a new change inside liferay portal:

```bash
npm run build:liferay
# or
yarn build:liferay
```

If you want to develop inside the Liferay's Remote Web Components. Start an local web component server with:

```bash
npm run serve:liferay
# or
yarn serve:liferay
```

This will start a local server at `http://localhost:5000` contain the files that should be imported inside the `System Setting -> Widget Tools`. The paths that should be added are:

```
http://localhost:5000/liferay/js/2.chunk.min.js
http://localhost:5000/liferay/js/main.min.js
http://localhost:5000/liferay/js/runtime-main.min.js
```

### Production

Edit the `.env` file with your liferay server information's. To build the application to use it with the Liferay's Remote Web Components, run:

```bash
npm run build:liferay
# or
yarn build:liferay
```

Inside the `build/liferay` folder will contain the files that should be imported inside the `System Setting -> Widget Tools`. The paths that should be added are:

```
<URL>/liferay/js/2.chunk.min.js
<URL>/liferay/js/main.min.js
<URL>/liferay/js/runtime-main.min.js
```

## How to work with sass

### Adding styles

All the configuration to use sass with web components is already in place. To add additional styles **is important** to make sure to register all the `.scss` files in the `src/styles/provider.scss`.

```scss
@import "path/to/scss/file";
```

### Steps to reproduce the sass configuration

The implementation is based on [this article](https://dev.to/m4thieulavoie/how-i-managed-to-use-scss-inside-web-components-3lk9), to reproduce this work in a Create React App project do the following steps:

1. Eject your CRA project

```bash
npm run eject
# or
yarn eject
```

2. Edit the `config/webpack.config.js` file

```jsonc
module: {
  rules: [
    // ...other rules
    {
      test: sassRegex,
      exclude: /node_modules/,
      use: [
        "sass-to-string",
        {
          loader: "sass-loader",
          options: {
            sassOptions: {
              outputStyle: "compressed",
            },
          },
        },
      ],
    },
    // ...
  ]
}
```

3. Add the entry to the web component

```js
import StylesProvider from "./styles/provider.scss";

class WebComponent extends HTMLElement {
  constructor() {
    super();
    this.styleSass = document.createElement("style");
    this.styleComponentsHost = document.createElement("div");

    this.attachShadow({ mode: "open" });
  }

  connectedCallback() {
    this.styleSass.textContent = StylesProvider;

    this.shadowRoot.appendChild(this.styleComponentsHost);
    this.shadowRoot.appendChild(this.mountPoint);

    ReactDOM.render(
      <Providers>
        <App />
      </Providers>,
      this.mountPoint
    );
  }
}

if (!customElements.get(TAG_NAME)) {
  customElements.define(TAG_NAME, WebComponent);
}
```