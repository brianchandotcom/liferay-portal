@spira_Interest_Keywords_Blocklist @Keywords @Interest_Keywords_Blocklist @team_FARO @priority_4
Feature: Delete multiple keywords
	As a Business User, I should be able to delete multiple keywords

	Background: [Setup] Navigate to Interest Topic
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Keywords" page

	Scenario: Delete multiple keywords
		Given I click the "Add Keyword" button
		And I name multiple keywords:
			| test  |
			| block |
			| sites |
			| key   |
		And I click the "Send" button
		And I go to the "Keywords" page
		When I click the select all checkbox in the toolbar
		And I click the delete icon from the toolbar
		And I click the "Continue" button
		Then I should see text saying "You have not added keywords to the blocklist yet." on the page