@spira_Interest_Keywords_Blocklist @Keywords @Interest_Keywords_Blocklist @team_FARO @priority_4
Feature: Add multiple of the same keyword
	As a Business User, I should be able to see multiple of the same keyword added once

	Background: [Setup] Navigate to Interest Topics
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Keywords" page

	Scenario: Add multiple of the same keyword to blocklist
		Given I click the "Add Keyword" button
		When I name multiple keywords:
			| block |
			| block |
		And I click the "Send" button
		Then I should only see keyword named "block" in the table
		And I should see a success alert saying "Success:1 keyword added to the blocklist."