import { spawnSync } from 'child_process';

/**
 * Execute a command in a child process.
 *
 * @param   {object} params             Command parameters.
 * @param   {array}  [params.args]      Arguments to pass to command.
 * @param   {string} [params.command]   Command to execute.
 * @param   {string} [params.directory] Directory to execute command in.
 * @param   {string} [params.input]     Input to pass to command.
 * @returns {string}
 */
export function execCmd(params) {
  const { args = [], command, directory, input } = params;

  const childProcess = spawnSync(command, args, {
    cwd: directory,
    encoding: 'utf8',
    input,
  });

  if (childProcess.status !== 0) {
    if (childProcess.stderr) {
      console.log(Error(childProcess.stderr));
    }

    throw new Error(
      `Error executing command: ${command} ${args.join(' ')}\n` +
        childProcess.stdout.trim()
    );
  }

  if (childProcess.stdout) {
    return childProcess.stdout.trim();
  }
}
