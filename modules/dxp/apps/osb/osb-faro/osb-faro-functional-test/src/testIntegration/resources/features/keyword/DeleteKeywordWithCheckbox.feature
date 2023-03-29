@spira_Interest_Keywords_Blocklist @Keywords @Interest_Keywords_Blocklist @team_FARO @priority_4
Feature: Delete keyword using the check box
  	As a Business User, I should be able to delete a keyword using the check box

  	Background: [Setup] Navigate to Interests Topic
	  	* I go to the "Home" page
	  	* I login as "test@faro.io:test"
	  	* I should see the "Sites" page
	  	* I go to the "Keywords" page

	Scenario: Delete keyword check box
	  	Given I click the "Add Keyword" button
	  	And I name the keyword "test"
	  	And I click the "Send" button
	  	When I click keyword "test" checkbox
	  	And I click the delete icon from the toolbar
	  	And I click the "Continue" button
	  	Then I should not see keyword named "test" in the table