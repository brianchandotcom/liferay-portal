# GH Gif

[![NPM version](http://img.shields.io/npm/v/gh-gif.svg?style=flat)](http://npmjs.org/gh-gif)
[![NPM downloads](http://img.shields.io/npm/dm/gh-gif.svg?style=flat)](http://npmjs.org/gh-gif)
[![Build Status](http://img.shields.io/travis/node-gh/gh-gif/master.svg?style=flat)](https://travis-ci.org/node-gh/gh-gif)
[![Dependencies Status](http://img.shields.io/david/node-gh/gh-gif.svg?style=flat)](https://david-dm.org/node-gh/gh-gif)
[![DevDependencies Status](http://img.shields.io/david/dev/node-gh/gh-gif.svg?style=flat)](https://david-dm.org/node-gh/gh-gif#info=devDependencies)

![DaftPunktocat](https://cloud.githubusercontent.com/assets/398893/3528215/09d7c124-078d-11e4-8ed7-683d3a0936f6.gif)

NodeGH plugin for commenting on pull requests/issues using GIF reactions.

> Maintained by [Zeno Rocha](https://github.com/zenorocha) and [Eduardo Lundgren](https://github.com/eduardolundgren).

## Install

```
[sudo] npm install -g gh gh-gif
```

## Usage

```
gh gif
```

Option             | Usage        | Type
---                | ---          | ---
`-n`, `--number`   | **Required** | `Number`
`-R`, `--reaction` | **Required** | `String`
`-c`, `--comment`  | *Optional*   | `String`
`-b`, `--best`     | *Optional*   | `Boolean`
`-B`, `--bestof`   | *Optional*   | `Number`
`-i`, `--image`    | *Optional*   | `String`
`-r`, `--repo`     | *Optional*   | `String`
`-u`, `--user`     | *Optional*   | `String`

#### Examples

* Comment on pull request/issue #75 with a happy reaction.

    ```
gh gif 75 --reaction happy
    ```

You can also use --comment

    ```
gh gif 75 --comment "This is cool" --reaction congratulation
    ```

* Comment on pull request/issue #75 of a certain repo with a surprised reaction.

    ```
gh gif 75 --reaction surprised --user node-gh --repo gh-gif
    ```

* Comment on pull request/issue #75 using a certain image.

    ```
gh gif 75 --image http://media1.giphy.com/media/5DQdk5oZzNgGc/original.gif
    ```

* Comment on multiple pull requests/issues at the same time.

	```
gh gif --reaction happy --number 1 --number 2
	```

* Comment on pull request/issue #75 with the best search result

    ```
gh gif 75 --reaction happy --best
    ```

* Comment on pull request/issue #75 with a random GIF of the first three result items.

    ```
gh gif 75 --reaction happy --bestof 3
    ```

## Testing

Check [Travis](https://travis-ci.org/node-gh/gh-gif) for continous integration results.

* Run [JSHint](http://www.jshint.com/), a tool to detect errors and potential problems.

    ```
npm run-script lint
    ```

* Run [Mocha](http://visionmedia.github.io/mocha/), a unit test framework.

    ```
npm run-script test
    ```

## Contributing

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request :D

## History

Check [Release](https://github.com/node-gh/gh-gif/releases) list.

## License

[BSD License](https://github.com/node-gh/gh/blob/master/LICENSE.md)
