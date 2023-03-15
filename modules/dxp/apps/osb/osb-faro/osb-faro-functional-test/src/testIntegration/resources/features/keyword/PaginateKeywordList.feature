@spira_Interest_Keywords_Blocklist @Keywords @Interest_Keywords_Blocklist @team_FARO @priority_4
Feature: Paginate keyword block list
	As a Business User, I should be able to paginate keyword block list

	Background: [Setup] Navigate to the Interest Topics
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Keywords" page

	Scenario: Paginate keyword block list
		Given I click the "Add Keyword" button
		And I name multiple keywords:
			| keyword 	|
			| test		|
			| block		|
			| paginate 	|
			| name		|
			| list		|
		And I click the "Send" button
		When I set the pagination delta to "5"
		And I go to page "2" in the table
		Then I should only see keyword named "test" in the table