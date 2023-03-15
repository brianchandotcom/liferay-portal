@spira_Interest_Keywords_Blocklist @Keywords @Interest_Keywords_Blocklist @team_FARO @priority_4
Feature: Search for a blocked keyword
	As a Business User, I should be able to search for a blocked keyword

	Background: [Setup] Navigate to Interests Topic
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Keywords" page

	Scenario: Search for a blocked keyword
		Given I click the "Add Keyword" button
		And I name multiple keywords:
			| keyword 	|
			| test 		|
			| block		|
		And I click the "Send" button
		When I search for "block"
		Then I should only see keywords named "block" in the table