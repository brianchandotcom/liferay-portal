## Portal Workflow Metrics Web

### Install

```
npm install
```

### Build

```
npm run build
```

### Technology Stack

- [Javascript ES6](http://es6-features.org/#Constants)
- [SASS](https://sass-lang.com/)
- [ReactJS](https://reactjs.org/)

### Project Structure

- *src/main/resources/META-INF/resources/js*: Base app folder
    - *./components*: Modules folder
    - *./shared*: Shared components and utilities
    - *./index.js*: Initial script
- *src/main/resources/META-INF/resources/css* - Base scss folder

### Code Quality

Syntax validation using [eslint](https://eslint.org/).

```
npm run lint
```

### Code Test

```
npm run test
```

Then open build/coverage/lcov-report/index.html in browser.

### Code Documentation

```
npm run doc:code
```

Then open build/doc/index.html in browser.